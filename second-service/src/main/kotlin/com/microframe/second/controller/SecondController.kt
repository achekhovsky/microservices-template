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

    @GetMapping(value = ["/id/{secondId}"])
    fun getSecondById(
        @PathVariable("secondId") secondId:String,
        @RequestHeader(value = "Accept-Language",required = false) strLocale: String
    ): ResponseEntity<SecondServiceModel> {
        val locale = Locale(strLocale)
        var second: SecondServiceModel = secondService.getSecondById(secondId, locale)
        return ResponseEntity.ok(second)
    }

    @GetMapping(value = ["/{secondName}"])
    fun getSecondByName(
        @PathVariable("secondName") secondName:String,
        @RequestHeader(value = "Accept-Language",required = false) strLocale: String
    ): ResponseEntity<SecondServiceModel> {
        val locale = Locale(strLocale)
        var second: SecondServiceModel = secondService.getSecondByName(secondName, locale)
        return ResponseEntity.ok(second)
    }

    @PutMapping(value = ["/id/{secondId}"])
    fun updateSecond(
        @PathVariable("secondId") secondId:String,
        @RequestBody request: SecondServiceModel,
        @RequestHeader(value = "Accept-Language",required = false) strLocale: String
    ): ResponseEntity<SecondServiceModel> {
        val locale = Locale(strLocale)
        var updatable = secondService.getSecondById(secondId, locale)
        updatable.secondName = request.secondName
        updatable.someData = request.someData
        updatable.description = request.description

        return ResponseEntity.ok(secondService.updateSecond(updatable, locale))
    }

    @PostMapping
    fun createSecond(
        @RequestBody request: SecondServiceModel,
        @RequestHeader(value = "Accept-Language",required = false) strLocale: String
    ): ResponseEntity<SecondServiceModel> {
        val locale = Locale(strLocale)
        return ResponseEntity.ok(secondService.createSecond(request, locale))
    }

    @DeleteMapping(value = ["/id/{secondId}"])
    fun deleteSecond(
        @PathVariable("secondId") secondId:String,
        @RequestHeader(value = "Accept-Language",required = false) strLocale: String
    ): ResponseEntity<String> {
        val locale = Locale(strLocale)
        return ResponseEntity.ok(secondService.deleteSecond(secondId, locale))
    }

}