package com.microframe.second

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
//See https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/web/config/EnableSpringDataWebSupport.html
@EnableSpringDataWebSupport
@EnableMongoRepositories("com.microframe.second.repository")
//Automatically updating Spring properties from the config server via the /refresh endpoint
@RefreshScope
class SecondServiceApplication {
}

fun main(args: Array<String>) {
	runApplication<SecondServiceApplication>(*args)

}
