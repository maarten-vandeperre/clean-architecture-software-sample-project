package com.redhat.demo.appdev.personservice.core.domain.person

import com.redhat.demo.appdev.personservice.core.domain.address.Address
import com.redhat.demo.appdev.personservice.core.domain.common.Date
import java.util.UUID

typealias PersonRef = UUID
data class Person(
        val ref: PersonRef,
        val firstName: String,
        val lastName: String,
        val birthDate: Date,
        val address: Address
) {
    val name = "$firstName $lastName"
}