package com.redhat.demo.appdev.personservice.core.dataproviders.inmemory.person

import com.redhat.demo.appdev.personservice.core.domain.person.Person

class DbPerson(
        val id: Long,
        val data: Person
)