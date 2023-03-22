package com.microframe.custom.utils.filters

import com.microframe.custom.utils.service.UserContext
import com.microframe.custom.utils.service.UserContextHolder

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory


open class UserContextFilter: Filter {
    private val logger: Logger = LoggerFactory.getLogger(UserContextFilter::class.java)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpServletRequest = request as HttpServletRequest
        UserContextHolder.getContext().trackId = httpServletRequest.getHeader(UserContext.TRACK_ID) ?: ""
        UserContextHolder.getContext().userId = httpServletRequest.getHeader(UserContext.USER_ID) ?: ""
        UserContextHolder.getContext().authToken = httpServletRequest.getHeader(UserContext.AUTH_TOKEN) ?: ""

        chain?.doFilter(httpServletRequest, response)
    }
}