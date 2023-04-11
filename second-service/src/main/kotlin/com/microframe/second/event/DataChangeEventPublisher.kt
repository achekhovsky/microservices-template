package com.microframe.second.event

import com.microframe.custom.utils.event.DataChangeEventModel
import com.microframe.custom.utils.service.UserContextHolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class DataChangeEventPublisher {
    private val logger: Logger = LoggerFactory.getLogger(DataChangeEventPublisher::class.java)
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, DataChangeEventModel>

    public fun publishDataChangeEvent(action: DataChangeEventModel.Actions, serviceInstanceId: String, serviceInstanceName: String) {
        logger.debug("Sending Kafka message for service instance with id: $serviceInstanceId and change action $action");
        val msg = DataChangeEventModel(
            UserContextHolder.getContext().trackId,
            action,
            serviceInstanceId,
            serviceInstanceName
        )

        val future = kafkaTemplate.send(MessageBuilder.withPayload(msg).build())

        future.whenComplete { result, ex ->
            if (ex == null) {
                logger.debug("Sent message=[$msg] with offset=[${result.recordMetadata.offset()}]")
            } else {
                logger.debug("Unable to send message=[$msg] due to : ${ex.message}")
            }
        }
    }
}