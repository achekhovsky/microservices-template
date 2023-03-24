package com.microframe.first.repository.redis

import com.microframe.first.model.SecondServiceModel
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.data.keyvalue.repository.KeyValueRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SecondServiceRedisRepository: KeyValueRepository<SecondServiceModel, String> {

}