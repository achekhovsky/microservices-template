package com.microframe.first.repository

import com.microframe.first.model.FirstServiceModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FirstRepository: JpaRepository<FirstServiceModel, Long> {
    fun findByFirstName(firstName: String): FirstServiceModel?
    fun findBySecondName(secondName: String): List<FirstServiceModel>?
    fun findByFirstNameAndSecondName(firstName: String, secondName: String): FirstServiceModel?
    fun deleteByFirstName(firstName: String)

}