package com.microframe.first.event

import com.microframe.custom.utils.event.DataChangeEventModel
import com.microframe.first.service.client.SecondServiceDiscoveryClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import java.util.*

@Configuration
class DataChangeEventListener {
    private val logger: Logger = LoggerFactory.getLogger(DataChangeEventListener::class.java)

    @Autowired
    private lateinit var ssdc: SecondServiceDiscoveryClient

    @KafkaListener(
        topics = ["\${common.data-change-topic}"],
        containerFactory = "kafkaListenerContainerFactory"
        )
    fun listenDataChangeEvent(
        @Payload message: DataChangeEventModel,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int,
        @Header(KafkaHeaders.GROUP_ID) group: String) {
        logger.debug("Received Kafka Message: $message from partition: $partition and group: $group")
        when(message.event) {
            DataChangeEventModel.Actions.CREATE -> {
                val secondServiceInstance = ssdc.getFromServiceById(message.serviceInstanceId, Locale.getDefault())
                secondServiceInstance?.let{
                    ssdc.updateCsh(it)
                    logger.debug("The cache was updated after CREATE event in the second service")
                }

            }
            DataChangeEventModel.Actions.UPDATE -> {
                val secondServiceInstance = ssdc.getFromServiceById(message.serviceInstanceId, Locale.getDefault())
                secondServiceInstance?.let{
                    ssdc.updateCsh(it)
                    logger.debug("The cache was updated after UPDATE event in the second service")
                }
            }
            DataChangeEventModel.Actions.DELETE -> {
                val secondServiceInstance = ssdc.getFromServiceById(message.serviceInstanceId, Locale.getDefault())
                secondServiceInstance?.let{
                    ssdc.updateCsh(it)
                    logger.debug("The cache was updated after DELETE event in the second service")
                }
            }
            else -> {logger.debug("It was just a GET, the cache was not updated")}
        }
    }
}