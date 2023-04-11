package com.microframe.first.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.*

@RedisHash("second")
data class SecondServiceModel(
    @Id
    var id: String = UUID.randomUUID().toString(),
    var secondName: String = "",
    var description: String = "",
    var someData: String = "") {
}