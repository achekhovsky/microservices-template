package com.microframe.first.controller

import com.microframe.first.model.FirstServiceModel
import com.microframe.first.service.FirstService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value=["v1/microFrame/{secondName}/first"])
class FirstController {

    @Autowired
    private lateinit var firstService: FirstService

    @GetMapping(value = ["/{firstName}"])
    fun getFirst(
        @PathVariable("firstName") firstName:String,
        @PathVariable("secondName") secondName:String,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<FirstServiceModel> {
        var first: FirstServiceModel = firstService.getFirst(firstName, secondName, locale)
        first.add(
            linkTo<FirstController> {
                getFirst(first.firstName, secondName, locale)
            }
            .withSelfRel(),
            linkTo<FirstController> {
                updateFirst(secondName, firstName, first, locale)
            }
                .withRel("updateFirst"),
            linkTo<FirstController> {
                createFirst(secondName, first, locale)
            }
                .withRel("createFirst"),
            linkTo<FirstController> {
                deleteFirst(first.firstName, secondName, locale)
            }
                .withRel("deleteFirst")
        )
        return ResponseEntity.ok(first)
    }

    @PutMapping(value = ["/{firstName}"])
    fun updateFirst(
        @PathVariable("secondName") secondName:String,
        @PathVariable("firstName") firstName:String,
        @RequestBody request: FirstServiceModel,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<FirstServiceModel> {
        var updatable = firstService.getFirst(firstName, secondName, locale)
        updatable.firstName = request.firstName
        updatable.secondName = request.secondName
        updatable.commentField = request.commentField
        updatable.description = request.commentField
        updatable.spareField = request.spareField
        return ResponseEntity.ok(firstService.updateFirst(updatable, locale))
    }

    @PostMapping
    fun createFirst(
        @PathVariable("secondName") secondName:String,
        @RequestBody request: FirstServiceModel,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<FirstServiceModel> {
        return ResponseEntity.ok(firstService.createFirst(request, locale))
    }

    @DeleteMapping(value = ["/{firstName}"])
    fun deleteFirst(
        @PathVariable("firstName") firstName:String,
        @PathVariable("secondName") secondName:String,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<String> {
        return ResponseEntity.ok(firstService.deleteFirst(firstName, locale))
    }

}