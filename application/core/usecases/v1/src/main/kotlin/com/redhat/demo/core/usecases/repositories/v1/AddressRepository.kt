package com.redhat.demo.core.usecases.repositories.v1

import java.util.*

interface AddressRepository {

    fun save(address: DbAddress): String
    fun exists(ref: UUID): Boolean
    fun delete(ref: UUID)
    fun get(ref: UUID): DbAddress?
    fun search(): List<DbAddress>

    data class DbAddress(
        val ref: UUID,
        val addressLine1: String,
        val addressLine2: String,
        val addressLine3: String?,
        val countryIsoCode: String
    )
}