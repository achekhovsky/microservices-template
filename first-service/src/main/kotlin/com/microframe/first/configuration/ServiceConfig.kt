package com.microframe.first.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "echo")
@ConfigurationPropertiesScan
//Test settings received from the configuration server
data class ServiceConfig(var profile:String, var name:String) {

}