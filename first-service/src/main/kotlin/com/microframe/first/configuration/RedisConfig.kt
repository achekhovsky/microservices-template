package com.microframe.first.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer

@Configuration
class RedisConfig {

    @Value("\${redis.server}")
    private var  redisServer: String = "redis"

    @Value("\${redis.port}")
    private var redisPort: Int = 6379

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory? {
        val redisConfig = RedisStandaloneConfiguration(redisServer, redisPort)
        return JedisConnectionFactory(redisConfig)
    }

    @Bean
    @Primary
    fun redisTemplate(): RedisTemplate<String, Any>? {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(jedisConnectionFactory()!!)
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        template.setEnableTransactionSupport(false)
        return template
    }
}