package com.microframe.custom.utils.interceptors

import com.microframe.custom.utils.service.UserContext
import com.microframe.custom.utils.service.UserContextHolder
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

open class UserContextInterceptor: ClientHttpRequestInterceptor {
    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        val headers = request.headers
        headers.add(UserContext.TRACK_ID, UserContextHolder.getContext().trackId)
        headers.add(UserContext.USER_ID, UserContextHolder.getContext().userId)
        return execution.execute(request, body)
    }
}