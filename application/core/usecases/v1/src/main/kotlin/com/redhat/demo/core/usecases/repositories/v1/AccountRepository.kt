package com.redhat.demo.core.usecases.repositories.v1

import java.util.*

interface AccountRepository {
    fun search(): List<DbAccount>

    data class DbAccount(
        val personRef: UUID,
        val firstName: String,
        val lastName: String,
        val birthDate: String?,
        val addressRef: UUID,
        val addressLine1: String,
        val addressLine2: String,
        val addressLine3: String?,
        val countryIsoCode: String
    )
}