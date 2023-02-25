package com.microframe.second.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationProperties(prefix = "echo")
@ConfigurationPropertiesScan
data class ServiceConfig(var profile:String, var name:String) {

}