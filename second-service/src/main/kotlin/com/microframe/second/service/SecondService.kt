package com.microframe.second.service

import com.microframe.second.model.SecondServiceModel
import com.microframe.second.repository.SecondRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*

@Service
open class SecondService {
    @Autowired
    lateinit var messages: MessageSource
    @Autowired
    lateinit var secondRepository: SecondRepository

    fun getSecond(secondName: String, locale: Locale): SecondServiceModel {
        var fm: SecondServiceModel? = secondRepository.findBySecondName(secondName)
        fm?.let {
            return it
        }
        throw IllegalArgumentException(
            String.format(messages.getMessage(
                "second.search.error.message", null, locale), null, secondName));
    }

    fun createSecond(second: SecondServiceModel, locale: Locale):SecondServiceModel {
        second.apply {
            secondName = when(secondName) {
                "" -> String.format("secondName_%s", UUID.randomUUID().toString().dropLast(20))
                else -> {
                    secondName
                }
            }
        }
        secondRepository.save(second)
        return second
    }

    fun updateSecond(second: SecondServiceModel, locale: Locale):SecondServiceModel {
        secondRepository.save(second)
        return second
    }

    fun deleteSecond(secondName: String, locale: Locale):String {
        var secondForDel = secondRepository.findBySecondName(secondName)
        var responseMessage = String.format(messages.getMessage("second.delete.message", null, locale),
            secondForDel?.id, secondForDel?.secondName)
        secondRepository.deleteBySecondName(secondName)
        return responseMessage
    }
}