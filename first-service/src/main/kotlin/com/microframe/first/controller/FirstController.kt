package com.microframe.first.controller

import com.microframe.first.model.FirstModel
import com.microframe.first.service.FirstService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value=["v1/microFrame/{secondId}/first"])
class FirstController {

    @Autowired
    private lateinit var firstService: FirstService

    @GetMapping(value = ["/{firstId}"])
    fun getFirst(
        @PathVariable("firstId") firstId:String,
        @PathVariable("secondId") secondId:String,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<FirstModel> {
        var first: FirstModel = firstService.getFirst(firstId, secondId)
        first.add(
            linkTo<FirstController> {
                getFirst(first.firstId, secondId, locale)
            }
            .withSelfRel(),
            linkTo<FirstController> {
                updateFirst(secondId, first, locale)
            }
                .withRel("updateFirst"),
            linkTo<FirstController> {
                createFirst(secondId, first, locale)
            }
                .withRel("createFirst"),
            linkTo<FirstController> {
                deleteFirst(first.firstId, secondId, locale)
            }
                .withRel("deleteFirst")
        )
        return ResponseEntity.ok(first)
    }

    @PutMapping
    fun updateFirst(
        @PathVariable("secondId") secondId:String,
        @RequestBody request: FirstModel,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<String> {
        return ResponseEntity.ok(firstService.updateFirst(request, secondId, locale))
    }

    @PostMapping
    fun createFirst(
        @PathVariable("secondId") secondId:String,
        @RequestBody request: FirstModel,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<String> {
        return ResponseEntity.ok(firstService.createFirst(request, secondId, locale))
    }

    @DeleteMapping(value = ["/{firstId}"])
    fun deleteFirst(
        @PathVariable("firstId") firstId:String,
        @PathVariable("secondId") secondId:String,
        @RequestHeader(value = "Accept-Language",required = false) locale: Locale
    ): ResponseEntity<String> {
        return ResponseEntity.ok(firstService.deleteFirst(firstId, secondId, locale))
    }

}