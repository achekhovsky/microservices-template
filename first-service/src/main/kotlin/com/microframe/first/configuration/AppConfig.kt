package com.microframe.first.configuration

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {
    @LoadBalanced
    @Bean
    fun getRestTemplate():RestTemplate {
        return RestTemplate()
    }
}