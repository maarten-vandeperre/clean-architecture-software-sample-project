package com.redhat.demo.appdev.personservice.configuration.config

import com.redhat.demo.appdev.personservice.core.usecases.factories.UseCasesFactory
import com.redhat.demo.appdev.personservice.core.usecases.person.AddPersonUseCase
import com.redhat.demo.appdev.personservice.core.usecases.person.GetPeopleUseCase
import com.redhat.demo.appdev.personservice.core.usecases.person.PersonRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class UseCasesConfig {//Quarkus VS Spring ==> class needs to be open over here

  @Bean
  open fun getPeopleUseCase(personRepository: PersonRepository): GetPeopleUseCase {//Quarkus VS Spring ==> method needs to be open over here
    return UseCasesFactory.getPeopleUseCase(personRepository = personRepository)
  }

  @Bean
  open fun addPersonUseCase(personRepository: PersonRepository): AddPersonUseCase {
    return UseCasesFactory.addPersonUseCase(personRepository = personRepository)
  }
}