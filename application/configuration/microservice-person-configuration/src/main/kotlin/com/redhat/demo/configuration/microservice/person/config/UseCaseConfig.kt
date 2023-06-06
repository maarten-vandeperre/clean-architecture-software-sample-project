package com.redhat.demo.configuration.microservice.person.config

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.v1.person.CreatePersonUseCase
import com.redhat.demo.core.usecases.v1.person.DefaultCreatePersonUseCase
import com.redhat.demo.core.usecases.v1.person.DefaultDeletePersonUseCase
import com.redhat.demo.core.usecases.v1.person.DefaultGetPersonUseCase
import com.redhat.demo.core.usecases.v1.person.DefaultSearchPeopleUseCase
import com.redhat.demo.core.usecases.v1.person.DefaultUpdatePersonUseCase
import com.redhat.demo.core.usecases.v1.person.DeletePersonUseCase
import com.redhat.demo.core.usecases.v1.person.GetPersonUseCase
import com.redhat.demo.core.usecases.v1.person.SearchPeopleUseCase
import com.redhat.demo.core.usecases.v1.person.UpdatePersonUseCase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class UseCaseConfig {

    @Produces
    fun createPersonUseCase(personRepository: PersonRepository): CreatePersonUseCase {
        return DefaultCreatePersonUseCase(personRepository)
    }

    @Produces
    fun updatePersonUseCase(personRepository: PersonRepository): UpdatePersonUseCase {
        return DefaultUpdatePersonUseCase(personRepository)
    }

    @Produces
    fun deletePersonUseCase(personRepository: PersonRepository): DeletePersonUseCase {
        return DefaultDeletePersonUseCase(personRepository)
    }

    @Produces
    fun getPersonUseCase(personRepository: PersonRepository): GetPersonUseCase {
        return DefaultGetPersonUseCase(personRepository)
    }

    @Produces
    fun searchPeopleUseCase(personRepository: PersonRepository): SearchPeopleUseCase {
        return DefaultSearchPeopleUseCase(personRepository)
    }

}