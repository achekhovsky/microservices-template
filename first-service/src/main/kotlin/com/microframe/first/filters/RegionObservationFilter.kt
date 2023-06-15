package com.microframe.first.filters

import io.micrometer.common.KeyValue
import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationFilter

class RegionObservationFilter(private val region: String): ObservationFilter {
    override fun map(context: Observation.Context): Observation.Context {
        return context
            .put("region", region)
            .addLowCardinalityKeyValue(KeyValue.of("region", region));
    }
}