package com.microframe.custom.utils.service

open class UserContext {
    companion object {
        @JvmStatic
        val TRACK_ID: String = "uctx-track-id"
        @JvmStatic
        val USER_ID: String = "uctx-user-id"
    }

    var trackId = String()
    var userId = String()

    override fun toString(): String {
        return "UserContext(trackId='$trackId', userId='$userId')"
    }


}