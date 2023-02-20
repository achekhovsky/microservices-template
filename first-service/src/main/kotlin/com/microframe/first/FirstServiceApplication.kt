package com.microframe.first

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
//See https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/web/config/EnableSpringDataWebSupport.html
@EnableSpringDataWebSupport
//Automatically updating Spring properties from the config server via the /refresh endpoint
@RefreshScope
class FirstServiceApplication

fun main(args: Array<String>) {
	runApplication<FirstServiceApplication>(*args)
}
