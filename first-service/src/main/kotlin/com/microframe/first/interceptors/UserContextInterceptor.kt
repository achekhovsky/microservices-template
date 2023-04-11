package com.microframe.first.interceptors

import com.microframe.custom.utils.service.UserContextHolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpResponse

class UserContextInterceptor: com.microframe.custom.utils.interceptors.UserContextInterceptor() {
    private val logger: Logger = LoggerFactory.getLogger(UserContextInterceptor::class.java)

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        logger.debug("UserContextInterceptor.intercept -> The user context has been set to response headers. ${UserContextHolder.getContext()}")
        return super.intercept(request, body, execution)
    }
}