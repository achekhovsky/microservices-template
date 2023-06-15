package com.microframe.first.service

import com.microframe.first.filters.UserContextFilter
import com.microframe.first.model.FirstServiceModel
import com.microframe.first.repository.FirstRepository
import com.microframe.first.service.client.SecondServiceDiscoveryClient
import io.github.resilience4j.bulkhead.annotation.Bulkhead
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*

@Service
open class FirstService {
    private val logger: Logger = LoggerFactory.getLogger(UserContextFilter::class.java)

    @Autowired
    lateinit var messages: MessageSource
    @Autowired
    lateinit var firstRepository: FirstRepository
    @Autowired
    lateinit var secondServiceDiscoveryClient: SecondServiceDiscoveryClient

    @Value(value = "\${common.data-change-topic}")
    private lateinit var commProp:String


    fun getFirst(firstName: String, secondId: String, locale: Locale): FirstServiceModel {
        val fm: FirstServiceModel? = firstRepository.findByFirstNameAndSecondId(firstName, secondId)
        fm?.let {
            var second = secondServiceDiscoveryClient.getSecond(secondId, locale)
            second?.let { sec -> it.description = String.format(messages.getMessage(
                "first.about.second.message", null, locale), sec.toString())
            }
            return it
        }
        throw IllegalArgumentException(
            String.format(messages.getMessage(
                "first.search.error.message", null, locale),
                firstName, secondId));
    }

    fun createFirst(first: FirstServiceModel, locale: Locale):FirstServiceModel {
        first.apply {
            firstName = String.format("firstId_%s", UUID.randomUUID())
        }
        firstRepository.save(first)
        return first
    }

    @Bulkhead(name = "firstServiceExternalBulkhead")
    fun updateFirst(first: FirstServiceModel, locale: Locale):FirstServiceModel {
        firstRepository.save(first)
        return first
    }

    fun deleteFirst(firstName: String, locale: Locale):String {
        var firstForDel = firstRepository.findByFirstName(firstName)
        var responseMessage = String.format(messages.getMessage("first.delete.message", null, locale),
            firstName, firstForDel.secondId)
        firstRepository.deleteByFirstName(firstName)
        return responseMessage
    }

    fun findFirstBySecondId(secondId: String):List<FirstServiceModel> {
        return firstRepository.findBySecondId(secondId)
    }


}