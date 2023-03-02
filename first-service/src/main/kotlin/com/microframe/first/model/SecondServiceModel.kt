package com.microframe.first.model

import org.springframework.data.annotation.Id
import java.util.*

data class SecondServiceModel(
    var id: String = UUID.randomUUID().toString(),
    var secondName: String = "",
    var description: String = "",
    var someData: String = "") {
}