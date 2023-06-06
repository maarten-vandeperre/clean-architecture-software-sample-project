package com.redhat.demo.appdev.personservice.core.usecases.person

import com.redhat.demo.appdev.personservice.core.domain.person.Person
import com.redhat.demo.appdev.personservice.core.coreutils.ExecutionResult

interface GetPeopleUseCase {
    fun execute(): ExecutionResult<List<Person>>
}

internal class DefaultGetPeopleUseCase(
        private val personRepository: PersonRepository
) : GetPeopleUseCase{
    override fun execute(): ExecutionResult<List<Person>> {
        return personRepository.getPeople()
    }

}