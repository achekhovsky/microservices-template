package com.microframe.first.configuration

import com.microframe.custom.utils.event.DataChangeEventModel
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig {
    private val logger: Logger = LoggerFactory.getLogger(KafkaConsumerConfig::class.java)

    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private var kafkaAddress = "localhost:9092"
    @Value(value = "\${common.data-change-group-id}")
    private var groupId:String = "changeGroup"
    @Value(value = "\${common.data-change-trusted-packages}")
    private var trustedPackages:String = "java.util, java.lang"

    @Bean
    fun kafkaAdmin(): KafkaAdmin? {
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, DataChangeEventModel> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaAddress
        //Or app.property spring.kafka.consumer.group-id: group-id
        configProps[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        //Or app.property spring.kafka.consumer.key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
        configProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        //Or app.property spring.kafka.consumer.value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
        configProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        configProps[JsonDeserializer.TRUSTED_PACKAGES] = trustedPackages
        return DefaultKafkaConsumerFactory(configProps)
    }

    @Bean("kafkaListenerContainerFactory")
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, DataChangeEventModel>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, DataChangeEventModel>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}