package com.microframe.gateway.filters

import com.microframe.gateway.filters.utils.FilterUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class TrackingFilter: GlobalFilter {
    private val logger: Logger = LoggerFactory.getLogger(TrackingFilter::class.java)

    override fun filter(exchange: ServerWebExchange?, chain: GatewayFilterChain?): Mono<Void>? {
        val requestHeader: HttpHeaders = exchange?.request?.headers ?: HttpHeaders()
        var resultExchange = exchange
        if(FilterUtils.isHeaderPresent(requestHeader, FilterUtils.TRACK_ID)) {
            logger.debug("TrackingFilter.filter -> TrackId already exists. track[FilterUtils.getTrackId(requestHeader)] ")
        } else {
            val track = FilterUtils.generateTrackId()
            resultExchange = exchange?.let { FilterUtils.setTrackId(it, track) }
            logger.debug("TrackingFilter.filter -> The trackId was generated inside filter. track[$track] ")
        }
        if(FilterUtils.isHeaderPresent(requestHeader, FilterUtils.USER_ID)) {
            logger.debug("TrackingFilter.filter -> UserId already exists. id[FilterUtils.getTrackId(requestHeader)] ")
        } else {
            val userId = FilterUtils.generateTrackId()
            resultExchange = exchange?.let { FilterUtils.setRequestHeader(it, FilterUtils.USER_ID, userId.toString()) }
            logger.debug("TrackingFilter.filter -> The userId was generated inside filter. id[$userId] ")
        }
        return chain?.filter(resultExchange)
    }
}