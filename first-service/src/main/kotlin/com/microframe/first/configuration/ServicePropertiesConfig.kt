package com.microframe.first.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
//Test settings received from the configuration server
class ServicePropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "echo")
    fun appTestProperties(): PropertiesTestClass? {
        return PropertiesTestClass()
    }
}