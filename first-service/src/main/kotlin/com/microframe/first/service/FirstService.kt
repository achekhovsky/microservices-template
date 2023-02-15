package com.microframe.first.service

import com.microframe.first.model.FirstModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*
import kotlin.random.Random

@Service
open class FirstService {
    @Autowired
    lateinit var messages: MessageSource

    fun getFirst(firstId: String, secondId: String): FirstModel {
        val fm: FirstModel = FirstModel(Random.nextInt(0, 10000))
        fm.firstId = firstId
        fm.secondId = secondId
        fm.description = "Description"
        fm.name = "Default name"
        fm.spareField = "Default spare field value"
        return fm
    }

    fun createFirst(first: FirstModel?, secondId: String, locale: Locale):String? {
        var responseMessage: String? = null
        first?.let {
            it.secondId = secondId
            responseMessage = String.format(messages.getMessage("first.create.message", null, locale), first.toString())
        }
        return responseMessage
    }

    fun updateFirst(first: FirstModel?, secondId: String, locale: Locale):String? {
        var responseMessage: String? = null
        first?.let {
            it.secondId = secondId
            responseMessage = String.format(messages.getMessage("first.update.message", null, locale), first.toString())
        }
        return responseMessage
    }

    fun deleteFirst(firstId: String, secondId: String, locale: Locale):String? {
        var responseMessage: String? = null
        responseMessage = String.format(messages.getMessage("first.delete.message", null, locale),
            firstId, secondId)
        return responseMessage
    }


}