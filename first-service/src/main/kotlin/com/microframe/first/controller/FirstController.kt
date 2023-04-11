package com.microframe.first.controller

import com.microframe.first.model.FirstServiceModel
import com.microframe.first.service.FirstService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value=["v1/microFrame/{secondId}/first"])
class FirstController {

    @Autowired
    private lateinit var firstService: FirstService

    @GetMapping(value = ["/{firstName}"])
    fun getFirst(
        @PathVariable("firstName") firstName:String,
        @PathVariable("secondId") secondId:String,
        @RequestHeader(value = "Accept-Language",required = false) strLocale: String
    ): ResponseEntity<FirstServiceModel> {
        val locale = Locale(strLocale)
        var first: FirstServiceModel = firstService.getFirst(firstName, secondId, locale)
        first.add(
            linkTo<FirstController> {
                getFirst(first.firstName, secondId, strLocale)
            }
            .withSelfRel(),
            linkTo<FirstController> {
                updateFirst(secondId, firstName, first, strLocale)
            }
                .withRel("updateFirst"),
            linkTo<FirstController> {
                createFirst(secondId, first, strLocale)
            }
                .withRel("createFirst"),
            linkTo<FirstController> {
                deleteFirst(first.firstName, secondId, strLocale)
            }
                .withRel("deleteFirst")
        )
        return ResponseEntity.ok(first)
    }

    @PutMapping(value = ["/{firstName}"])
    fun updateFirst(
        @PathVariable("secondId") secondId:String,
        @PathVariable("firstName") firstName:String,
        @RequestBody request: FirstServiceModel,
        @RequestHeader(value = "Accept-Language",required = false) strLocale: String
    ): ResponseEntity<FirstServiceModel> {
        val locale = Locale(strLocale)
        var updatable = firstService.getFirst(firstName, secondId, locale)
        updatable.firstName = request.firstName
        updatable.secondId = request.secondId
        updatable.commentField = request.commentField
        updatable.description = request.commentField
        updatable.spareField = request.spareField
        return ResponseEntity.ok(firstService.updateFirst(updatable, locale))
    }

    @PostMapping
    fun createFirst(
        @PathVariable("secondId") secondId:String,
        @RequestBody request: FirstServiceModel,
        @RequestHeader(value = "Accept-Language",required = false) strLocale: String
    ): ResponseEntity<FirstServiceModel> {
        val locale = Locale(strLocale)
        return ResponseEntity.ok(firstService.createFirst(request, locale))
    }

    @DeleteMapping(value = ["/{firstName}"])
    fun deleteFirst(
        @PathVariable("firstName") firstName:String,
        @PathVariable("secondId") secondId:String,
        @RequestHeader(value = "Accept-Language",required = false) strLocale: String
    ): ResponseEntity<String> {
        val locale = Locale(strLocale)
        return ResponseEntity.ok(firstService.deleteFirst(firstName, locale))
    }

}