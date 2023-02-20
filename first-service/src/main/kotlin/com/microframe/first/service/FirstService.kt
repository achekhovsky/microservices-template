package com.microframe.first.service

import com.microframe.first.model.FirstServiceModel
import com.microframe.first.repository.FirstRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*

@Service
open class FirstService {
    @Autowired
    lateinit var messages: MessageSource
    @Autowired
    lateinit var firstRepository: FirstRepository

    fun getFirst(firstName: String, secondName: String, locale: Locale): FirstServiceModel {
        val fm: FirstServiceModel? = firstRepository.findByFirstNameAndSecondName(firstName, secondName)
        fm?.let {
            return it
        }
        throw IllegalArgumentException(
            String.format(messages.getMessage(
                "first.search.error.message", null, locale),
                firstName, secondName));
    }

    fun createFirst(first: FirstServiceModel, locale: Locale):FirstServiceModel {
        first.apply {
            firstName = String.format("firstId_%s", UUID.randomUUID())
        }
        firstRepository.save(first)
        return first
    }

    fun updateFirst(first: FirstServiceModel, locale: Locale):FirstServiceModel {
        firstRepository.save(first)
        return first
    }

    fun deleteFirst(firstName: String, locale: Locale):String {
        var firstForDel = firstRepository.findByFirstName(firstName)
        var responseMessage = String.format(messages.getMessage("first.delete.message", null, locale),
            firstName, firstForDel?.secondName)
        firstRepository.deleteByFirstName(firstName)
        return responseMessage
    }


}