package com.microframe.custom.utils.event

data class DataChangeEventModel(val trackId: String, val event: Actions, val serviceInstanceId: String, val serviceInstanceName: String): java.io.Serializable {
    enum class Actions {GET, CREATE, UPDATE, DELETE}
}