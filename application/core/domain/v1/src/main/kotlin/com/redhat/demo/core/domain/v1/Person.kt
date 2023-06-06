package com.redhat.demo.core.domain.v1

data class Person(
    val firstName: String,
    val lastName: String,
    val birthDate: String?,
    val address: String?
)