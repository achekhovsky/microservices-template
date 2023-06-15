package com.microframe.first.service.client

import com.microframe.custom.utils.wrappers.ObservationWrapperUtil
import com.microframe.first.model.SecondServiceModel
import com.microframe.first.repository.redis.SecondServiceRedisRepository
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.micrometer.observation.ObservationRegistry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Component
class SecondServiceDiscoveryClient {
    private val logger: Logger = LoggerFactory.getLogger(SecondServiceDiscoveryClient::class.java)
    private val serviceUriById = "http://second-service/v1/microFrame/second/id/{secondId}"
//    private val serviceUriById = "http://gateway-server/sservice/v1/microFrame/second/id/{secondId}"

    @Autowired
    private lateinit var restTemplate: RestTemplate
    @Autowired
    private lateinit var secondServiceRedisRepository: SecondServiceRedisRepository
    @Autowired
    private lateinit var observationRegistry: ObservationRegistry



    //Breaker for the services interaction
    @CircuitBreaker(name = "firstServiceExternalCBreaker")
    @Retry(name = "firstServiceExternalRetry")
    fun getSecond (secondId: String, locale: Locale): SecondServiceModel? {
        return ObservationWrapperUtil.wrapWithEventAndInvoke(
            observationRegistry,
            "span.name",
            "Getting second service instance from first service",
            "start.event",
            "end.event",
            listOf("secondId" to secondId)
        ) {
            try {
                checkNotNull(checkCsh(secondId)) { logger.error("Second instance with id [$secondId] not found in Redis cash.") }
            } catch (ex: Exception) {
                val secondFromService = getFromServiceById(secondId, locale)
                secondFromService?.let { updateCsh(it) }
                secondFromService
            }
        }
    }

    private fun checkCsh(secondId: String):  SecondServiceModel? {
        return try {
            val fromCash = secondServiceRedisRepository.findById(secondId).getOrNull()
            logger.debug("Second instance with id [$secondId] successfully retrieved from Redis.")
            fromCash
        } catch (ex: Exception) {
            logger.error("Error encountered while trying to retrieve second instance with id [$secondId] from Redis. Exception $ex")
            null;
        }
    }

    fun updateCsh(ssInstance: SecondServiceModel) {
        try {
            secondServiceRedisRepository.save(ssInstance)
            logger.debug("Second instance with id [${ssInstance.id}] successfully saved in Redis.")
        } catch (ex: Exception) {
            logger.error("Unable to cache second instance [$ssInstance] in Redis. Exception $ex")
        }
    }

    fun getFromServiceById(secondId: String, locale: Locale): SecondServiceModel? {
        //Get JWT from context then pass it to next request

        var restExchange = restTemplate.exchange(
            serviceUriById,
            HttpMethod.GET,
            RequestEntity.head(serviceUriById)
                .header(HttpHeaders.ACCEPT_LANGUAGE, locale.toLanguageTag())
                .header(HttpHeaders.AUTHORIZATION, getToken()).build(),
            SecondServiceModel::class.java,
            secondId)
        return restExchange.body
    }

    private fun getToken(): String {
        val oAuth2AuthenticationToken: JwtAuthenticationToken? = SecurityContextHolder.getContext().authentication as? JwtAuthenticationToken
        return "Bearer ${oAuth2AuthenticationToken?.token?.tokenValue}"
    }
}