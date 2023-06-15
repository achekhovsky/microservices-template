package com.microframe.custom.utils.wrappers

import io.micrometer.common.KeyValues
import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationRegistry
import io.micrometer.observation.annotation.Observed
import java.util.function.Supplier

class ObservationWrapperUtil {
    companion object {
        @JvmStatic
        fun <T> wrapAndInvoke(observationRegistry: ObservationRegistry,
                              spanName: String,
                              spanContextualName: String,
                              lowCardinalityKsVs: List<Pair<String, String>>,
                              f:() -> T):T {
            val kvs: KeyValues = KeyValues.of(lowCardinalityKsVs, { pair -> pair.first}, { pair -> pair.second})
            return Observation.createNotStarted(spanName, observationRegistry)
                .contextualName(spanContextualName)
                .lowCardinalityKeyValues(kvs)
                .observe(Supplier<T> { f.invoke() })
        }

        @JvmStatic
        fun <T> wrapWithEventAndInvoke(observationRegistry: ObservationRegistry,
                                       spanName: String,
                                       spanContextualName: String,
                                       startEventName: String,
                                       endEventName: String,
                                       lowCardinalityKsVs: List<Pair<String, String>>,
                                       f:() -> T):T {
            val kvs: KeyValues = KeyValues.of(lowCardinalityKsVs, { pair -> pair.first}, { pair -> pair.second})
            val observation = Observation.start(spanName, observationRegistry)
            observation.openScope().use {
                observation.contextualName(spanContextualName)
                observation.lowCardinalityKeyValues(kvs)
                observation.event(Observation.Event.of(startEventName))
                val result = f.invoke()
                observation.event(Observation.Event.of(endEventName))
                observation.stop()
                return result
            }
        }

        @JvmStatic
        @Observed(
            name = "annotation.observation",
            contextualName = "observed annotation showcase",
            lowCardinalityKeyValues = ["first.tag", "AnnotationWrapper"]
        )
        fun <T> annotationWrapper(f:() -> T):T {
            return f.invoke()
        }
    }

}