package com.redhat.demo.appdev.personservice.configuration.config

import com.redhat.demo.appdev.personservice.core.usecases.factories.UseCasesFactory
import com.redhat.demo.appdev.personservice.core.usecases.person.AddPersonUseCase
import com.redhat.demo.appdev.personservice.core.usecases.person.GetPeopleUseCase
import com.redhat.demo.appdev.personservice.core.usecases.person.PersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class UseCasesConfig {

  @Produces
  @ApplicationScoped
  fun getPeopleUseCase(personRepository: PersonRepository): GetPeopleUseCase {
    return UseCasesFactory.getPeopleUseCase(personRepository = personRepository)
  }

  @Produces
  @ApplicationScoped
  fun addPersonUseCase(personRepository: PersonRepository): AddPersonUseCase {
    return UseCasesFactory.addPersonUseCase(personRepository = personRepository)
  }
}