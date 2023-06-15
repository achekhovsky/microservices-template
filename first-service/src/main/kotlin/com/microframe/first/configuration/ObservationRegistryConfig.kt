package com.microframe.first.configuration

import com.microframe.first.filters.RegionObservationFilter
import io.micrometer.observation.ObservationRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.observation.ObservationRegistryCustomizer
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class ObservationRegistryConfig: ObservationRegistryCustomizer<ObservationRegistry> {

    @Value("\${region}")
    private val region: String = "default-region"

    override fun customize(registry: ObservationRegistry) {
        registry.observationConfig()
            .observationFilter(RegionObservationFilter(region));
    }
}