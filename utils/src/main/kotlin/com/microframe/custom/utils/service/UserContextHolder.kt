package com.microframe.custom.utils.service

import kotlin.concurrent.getOrSet

open class UserContextHolder {
    companion object {
        private val userContext: ThreadLocal<UserContext> = ThreadLocal()

        @JvmStatic
        fun getContext(): UserContext {
            return userContext.getOrSet { UserContext() }
        }

        @JvmStatic
        fun setContext(ctx: UserContext) {
            userContext.set(ctx)
        }
    }
}