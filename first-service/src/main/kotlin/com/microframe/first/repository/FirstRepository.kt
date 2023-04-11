package com.microframe.first.repository

import com.microframe.first.model.FirstServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FirstRepository: JpaRepository<FirstServiceModel, Long> {
    fun findByFirstName(firstName: String): FirstServiceModel
    fun findBySecondId(secondId: String): List<FirstServiceModel>
    fun findByFirstNameAndSecondId(firstName: String, secondId: String): FirstServiceModel
    fun deleteByFirstName(firstName: String)

}