package com.microframe.first

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableConfigurationProperties
//See https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/web/config/EnableSpringDataWebSupport.html
@EnableSpringDataWebSupport
//Automatically updating Spring properties from the config server via the /refresh endpoint
@EnableDiscoveryClient
@RefreshScope
class FirstServiceApplication

fun main(args: Array<String>) {
	runApplication<FirstServiceApplication>(*args)
}
