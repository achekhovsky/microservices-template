package com.microframe.second.configuration

import com.microframe.custom.utils.event.DataChangeEventModel
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class KafkaProducerConfig {
    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private var kafkaAddress = "localhost:9092"
    @Value(value = "\${common.data-change-topic}")
    private var topicName = "ssEvent"

    @Bean
    fun kafkaAdmin(): KafkaAdmin? {
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun secondServiceEventTopic(): NewTopic? {
        return NewTopic(topicName, 1, 1.toShort())
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, DataChangeEventModel> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaAddress
        //Or app.property spring.kafka.producer.key-serializer: org.apache.kafka.common.serialization.StringSerializer
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        //Or app.property spring.kafka.producer.value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java

        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, DataChangeEventModel> {
        val template = KafkaTemplate(producerFactory())
        template.defaultTopic = secondServiceEventTopic()?.name() ?: topicName
        return template
    }
}