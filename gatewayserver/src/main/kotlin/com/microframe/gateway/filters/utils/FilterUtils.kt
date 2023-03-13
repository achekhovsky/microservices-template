package com.microframe.gateway.filters.utils

import org.springframework.http.HttpHeaders
import org.springframework.web.server.ServerWebExchange
import java.util.UUID

object FilterUtils {
    const val TRACK_ID: String = "uctx-track-id"
    const val USER_ID: String = "uctx-user-id"

    fun getTrackId(requestHeader: HttpHeaders): String? {
        return requestHeader[TRACK_ID]?.first()
    }

    fun setTrackId(exchange: ServerWebExchange, trackId: UUID): ServerWebExchange {
       return setRequestHeader(exchange, TRACK_ID, trackId.toString())
    }

    fun generateTrackId(): UUID {
        return UUID.randomUUID()
    }

    fun setRequestHeader(exchange: ServerWebExchange, headerName: String, headerValue: String): ServerWebExchange {
        return exchange.mutate().request(exchange.request.mutate().header(headerName, headerValue).build()).build()
    }

    fun isHeaderPresent(requestHeader: HttpHeaders, headerName: String): Boolean {
        return when(requestHeader[headerName]) {
            null -> false
            else -> true
        }
    }

}