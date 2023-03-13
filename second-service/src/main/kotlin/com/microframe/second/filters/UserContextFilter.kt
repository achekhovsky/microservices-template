package com.microframe.second.filters

import com.microframe.custom.utils.service.UserContextHolder
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserContextFilter: com.microframe.custom.utils.filters.UserContextFilter() {
    private val logger: Logger = LoggerFactory.getLogger(UserContextFilter::class.java)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        super.doFilter(request, response, chain)
        logger.debug("UserContextFilter::doFilter -> The user context has been set. ${UserContextHolder.getContext()}")
    }
}