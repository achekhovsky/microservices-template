package com.microframe.gateway.filters

import com.microframe.gateway.filters.utils.FilterUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class ResponseFilter: GlobalFilter {
    private val logger: Logger = LoggerFactory.getLogger(ResponseFilter::class.java)

    override fun filter(exchange: ServerWebExchange?, chain: GatewayFilterChain?): Mono<Void>? {
        return chain?.filter(exchange)?.then(Mono.fromRunnable {
            val requestHeader: HttpHeaders = exchange?.request?.headers ?: HttpHeaders()
            FilterUtils.getTrackId(requestHeader)?.let {
                exchange?.response?.headers?.add(FilterUtils.TRACK_ID, it)
                logger.debug("ResponseFilter.filter -> The trackId was added to response headers. track[$it] ")
            }
            requestHeader[FilterUtils.USER_ID]?.first().let {
                exchange?.response?.headers?.add(FilterUtils.USER_ID, it)
                logger.debug("ResponseFilter.filter -> The userId was added to response headers. id[$it] ")
            }
        })
    }
}