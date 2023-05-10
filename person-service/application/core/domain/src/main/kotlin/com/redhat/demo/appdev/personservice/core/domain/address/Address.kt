package com.redhat.demo.appdev.personservice.core.domain.address

import java.util.*

enum class Country(val isoCode: String) {
  BE("BE"),
  NL("NL");
}

typealias AddressRef = UUID

data class Address(
        val ref: AddressRef,
        val addressLine1: String,
        val addressLine2: String,
        val country: Country
)