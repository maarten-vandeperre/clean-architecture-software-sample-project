package com.redhat.demo.core.usecases.repositories.v1

import java.util.*

interface PersonRepository {

    fun save(person: DbPerson): String
    fun exists(ref: UUID): Boolean
    fun delete(ref: UUID)
    fun get(ref: UUID): DbPerson?
    fun search(): List<DbPerson>

    data class DbPerson(
        val ref: UUID,
        val firstName: String,
        val lastName: String,
        val birthDate: String?,
        val addressRef: UUID?
    )
}