package com.microframe.first.configuration

import com.microframe.first.interceptors.UserContextInterceptor
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {
    @LoadBalanced
    @Bean
    fun getRestTemplate():RestTemplate {
        val restTmp = RestTemplate()
        restTmp.interceptors.apply {
            add(UserContextInterceptor())
        }
        return restTmp
    }
}