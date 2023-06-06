package com.redhat.demo.core.domain.v1

data class Address(
    val addressLine1: String,
    val addressLine2: String,
    val addressLine3: String?,
    val countryIsoCode: String
)
