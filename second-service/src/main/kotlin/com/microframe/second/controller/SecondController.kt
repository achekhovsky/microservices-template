package com.microframe.second.controller

import com.microframe.second.model.SecondServiceModel
import com.microframe.second.service.SecondService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value=["v1/microFrame/second"])
class SecondController {

    @Autowired
    private lateinit var secondService: SecondService

    @GetMapping(value = ["/{secondName}"])
    fun getSecond(
        @PathVariable("secondName") secondName:String,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<SecondServiceModel> {
        var second: SecondServiceModel = secondService.getSecond(secondName, locale)
        return ResponseEntity.ok(second)
    }

    @PutMapping(value = ["/{secondName}"])
    fun updateSecond(
        @PathVariable("secondName") secondName:String,
        @RequestBody request: SecondServiceModel,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<SecondServiceModel> {
        var updatable = secondService.getSecond(secondName, locale)
        updatable.secondName = request.secondName
        updatable.someData = request.someData
        updatable.description = request.description

        return ResponseEntity.ok(secondService.updateSecond(updatable, locale))
    }

    @PostMapping
    fun createSecond(
        @RequestBody request: SecondServiceModel,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<SecondServiceModel> {
        return ResponseEntity.ok(secondService.createSecond(request, locale))
    }

    @DeleteMapping(value = ["/{secondName}"])
    fun deleteSecond(
        @PathVariable("secondName") secondName:String,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<String> {
        return ResponseEntity.ok(secondService.deleteSecond(secondName, locale))
    }

}