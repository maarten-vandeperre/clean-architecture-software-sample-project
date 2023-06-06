package com.redhat.demo.core.domain.v1

data class ReadPerson(
    val ref: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String?
)