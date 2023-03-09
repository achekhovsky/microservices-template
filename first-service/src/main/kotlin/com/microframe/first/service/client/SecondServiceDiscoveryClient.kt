package com.microframe.first.service.client

import com.microframe.first.model.SecondServiceModel
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.*

@Component
class SecondServiceDiscoveryClient {
    @Autowired
    private lateinit var restTemplate: RestTemplate

    //Breaker for the services interaction
    @CircuitBreaker(name = "firstServiceExternalCBreaker")
    @Retry(name = "firstServiceExternalRetry")
    fun getSecond (secondName: String, locale: Locale): SecondServiceModel? {
        val serviceUri = "http://second-service/v1/microFrame/second/{secondName}"
        var restExchange = restTemplate.exchange(
            serviceUri,
            HttpMethod.GET,
            RequestEntity.head(serviceUri).header(HttpHeaders.ACCEPT_LANGUAGE, locale.toLanguageTag()).build(),
            SecondServiceModel::class.java,
            secondName)
        return restExchange.body
    }
}