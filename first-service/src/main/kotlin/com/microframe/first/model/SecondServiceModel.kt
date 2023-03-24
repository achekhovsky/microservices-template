package com.microframe.first.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.*

@RedisHash("second")
class SecondServiceModel(
    var id: String = UUID.randomUUID().toString(),
    @Id
    var secondName: String = "",
    var description: String = "",
    var someData: String = "") {
}