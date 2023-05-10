package com.redhat.demo.appdev.personservice.core.usecases.factories

import com.redhat.demo.appdev.personservice.core.usecases.person.*

object UseCasesFactory {
  fun getPeopleUseCase(personRepository: PersonRepository): GetPeopleUseCase {
    return DefaultGetPeopleUseCase(personRepository)
  }

  fun addPersonUseCase(personRepository: PersonRepository): AddPersonUseCase {
    return DefaultAddPersonUseCase(personRepository)
  }
}