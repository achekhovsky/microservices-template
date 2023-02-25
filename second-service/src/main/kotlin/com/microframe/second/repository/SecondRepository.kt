package com.microframe.second.repository

import com.microframe.second.model.SecondServiceModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import java.util.UUID

interface SecondRepository: MongoRepository<SecondServiceModel, String> {
    fun findBySecondName(@Param("secondName")secondName: String): SecondServiceModel?
    fun deleteBySecondName(@Param("secondName")secondName: String)
}