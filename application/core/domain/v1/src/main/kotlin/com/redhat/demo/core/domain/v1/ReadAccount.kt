package com.redhat.demo.core.domain.v1

data class ReadAccount (
    val personRef: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String?,
    val addressRef: String,
    val addressLine1: String,
    val addressLine2: String,
    val addressLine3: String?,
    val countryIsoCode: String
)