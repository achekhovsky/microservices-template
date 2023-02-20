package com.microframe.first.model

import jakarta.persistence.*
import org.hibernate.Hibernate
import org.springframework.hateoas.RepresentationModel
import java.util.UUID
import kotlin.random.Random

@Entity
@Table(name="first_service")
data class FirstServiceModel(
    @Id
    @Column(name = "first_id", nullable = false)
    var id: UUID = UUID.randomUUID(),
    @Column(name = "first_name", nullable = false, unique = true)
    var firstName: String = "",
    var description: String = "",
    @Column(name = "second_name", nullable = false)
    var secondName: String = "",
    @Column(name = "comment_field", nullable = false)
    var commentField: String = "",
    @Column(name = "spare_field", nullable = false)
    var spareField: String = ""): RepresentationModel<FirstServiceModel>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as FirstServiceModel

        return id == other.id
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + firstName.hashCode()
        return result
    }

    override fun toString(): String {
        return String.format("FirstModel(id=%s, firstName=%s, description=%s, secondName=%s, name=%s, spareField=%s)",
            id,
            firstName,
            description,
            secondName,
            commentField,
            spareField)
    }


}