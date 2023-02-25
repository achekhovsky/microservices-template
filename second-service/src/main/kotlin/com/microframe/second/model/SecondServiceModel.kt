package com.microframe.second.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Document("second_service")
open class SecondServiceModel constructor(val version: Float = 0.1F) {

    @Id
    var id: String = UUID.randomUUID().toString()
    @Field(name = "second_name")
    var secondName: String = ""
    var description: String = ""
    @Field(name = "some_data")
    var someData: String = ""

    //Provide an all-args constructor! This allows the object mapping to skip the property population for optimal performance.
    private constructor(id: String, version: Float, secondName: String, description: String, someData: String) : this(version) {
        this.id = id
        this.secondName = secondName
        this.description = description
        this.someData = someData
    }

    //If the property is immutable but exposes a with… method (see below), we use the with… method to create a new entity instance with the new property value.
    //See https://docs.spring.io/spring-data/commons/docs/2.5.5/reference/html/#mapping.property-population
    fun withVersion(version: Float): SecondServiceModel {
        return SecondServiceModel(this.id, version, this.secondName, this.description, this.someData)
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + secondName.hashCode()
        return result
    }

    override fun toString(): String {
        return String.format("SecondModel(id=%s, secondName=%s, description=%s, someData=%s)",
            id,
            secondName,
            description,
            someData)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as SecondServiceModel

        if (id != other.id) return false
        if (secondName != other.secondName) return false

        return true
    }
}