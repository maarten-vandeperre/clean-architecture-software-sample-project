package com.redhat.demo.appdev.personservice.core.usecases.person

import com.redhat.demo.appdev.personservice.core.coreutils.ExecutionResult
import com.redhat.demo.appdev.personservice.core.domain.person.Person
import com.redhat.demo.appdev.personservice.core.domain.person.PersonRef

interface PersonRepository {
  fun getPeople(): ExecutionResult<List<Person>> //TODO make it return a page object

  fun addPerson(person: Person): ExecutionResult<PersonRef>
}