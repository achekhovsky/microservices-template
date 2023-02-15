package com.microframe.first.model

import org.springframework.hateoas.RepresentationModel

data class FirstModel(
    val id: Int,
    var firstId: String = "",
    var description: String = "",
    var secondId: String = "",
    var name: String = "",
    var spareField: String = ""): RepresentationModel<FirstModel>() {
}