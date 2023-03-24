package com.microframe.first.service.client

import com.microframe.first.model.SecondServiceModel
import com.microframe.first.repository.redis.SecondServiceRedisRepository
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
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
    private val serviceUri = "http://second-service/v1/microFrame/second/{secondName}"

    @Autowired
    private lateinit var restTemplate: RestTemplate
    @Autowired
    private lateinit var secondServiceRedisRepository: SecondServiceRedisRepository


    //Breaker for the services interaction
    @CircuitBreaker(name = "firstServiceExternalCBreaker")
    @Retry(name = "firstServiceExternalRetry")
    fun getSecond (secondName: String, locale: Locale): SecondServiceModel? {
        return try {
            checkNotNull(checkCsh(secondName)) { logger.error("Second instance with name [$secondName] not found in Redis cash.") }
        } catch (ex: Exception) {
            val secondFromService = getFromService(secondName, locale)
            secondFromService?.let { updateCsh(it) }
            secondFromService
        }
    }

    private fun checkCsh(secondName: String):  SecondServiceModel? {
        return try {
            val fromCash = secondServiceRedisRepository.findById(secondName).getOrNull()
            logger.debug("Second instance with name [$secondName] successfully retrieved from Redis.")
            fromCash
        } catch (ex: Exception) {
            logger.error("Error encountered while trying to retrieve second instance with name [$secondName] from Redis. Exception $ex")
            null;
        }
    }

    private fun updateCsh(ssInstance: SecondServiceModel) {
        try {
            secondServiceRedisRepository.save(ssInstance)
            logger.debug("Second instance with name [${ssInstance.secondName}] successfully saved in Redis.")
        } catch (ex: Exception) {
            logger.error("Unable to cache second instance [$ssInstance] in Redis. Exception $ex")
        }
    }

    private fun getFromService(secondName: String, locale: Locale): SecondServiceModel? {
        //Get JWT from context then pass it to next request
        val oAuth2AuthenticationToken: JwtAuthenticationToken? = SecurityContextHolder.getContext().authentication as? JwtAuthenticationToken
        val token = "Bearer ${oAuth2AuthenticationToken?.token?.tokenValue}"
        var restExchange = restTemplate.exchange(
            serviceUri,
            HttpMethod.GET,
            RequestEntity.head(serviceUri)
                .header(HttpHeaders.ACCEPT_LANGUAGE, locale.toLanguageTag())
                .header(HttpHeaders.AUTHORIZATION, token).build(),
            SecondServiceModel::class.java,
            secondName)
        return restExchange.body
    }
}