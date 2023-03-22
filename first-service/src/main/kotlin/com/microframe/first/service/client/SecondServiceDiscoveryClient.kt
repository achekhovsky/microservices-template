package com.microframe.first.service.client

import com.microframe.first.model.SecondServiceModel
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

@Component
class SecondServiceDiscoveryClient {
    private val logger: Logger = LoggerFactory.getLogger(SecondServiceDiscoveryClient::class.java)
    @Autowired
    private lateinit var restTemplate: RestTemplate

    //Breaker for the services interaction
    @CircuitBreaker(name = "firstServiceExternalCBreaker")
    @Retry(name = "firstServiceExternalRetry")
    fun getSecond (secondName: String, locale: Locale): SecondServiceModel? {
        val serviceUri = "http://second-service/v1/microFrame/second/{secondName}"
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