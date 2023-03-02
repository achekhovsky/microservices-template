package com.microframe.first.service.client

import com.microframe.first.model.SecondServiceModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.*

@Component
class SecondServiceDiscoveryClient {
    @Autowired
    private lateinit var discoveryClient: DiscoveryClient

    fun getSecond (secondName: String, locale: Locale): SecondServiceModel? {
        var restTemplate:RestTemplate = RestTemplate()
        var instances: List<ServiceInstance> = discoveryClient.getInstances("second-service")

        instances.ifEmpty { return null}
        var serviceUri = String.format("%s/v1/microFrame/second/%s",
            instances[kotlin.random.Random.nextInt(instances.size)].uri.toString(),
            secondName);

        var restExchange = restTemplate.exchange(
            serviceUri,
            HttpMethod.GET,
            RequestEntity.head(serviceUri).header(HttpHeaders.ACCEPT_LANGUAGE, locale.toLanguageTag()).build(),
            SecondServiceModel::class.java,
            secondName)
        return restExchange.body
    }
}