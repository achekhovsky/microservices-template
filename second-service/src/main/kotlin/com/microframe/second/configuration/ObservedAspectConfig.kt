package com.microframe.second.configuration

import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.aop.ObservedAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class ObservedAspectConfig {

    //Before using the @Observed annotation it is necessary to register a bean of an ObservedAspect class.
    //If this is not done, the annotation will not be processed and no observation will be created
    @Bean
    fun observedAspect(observationRegistry: ObservationRegistry): ObservedAspect {
        return ObservedAspect(observationRegistry)
    }
}
