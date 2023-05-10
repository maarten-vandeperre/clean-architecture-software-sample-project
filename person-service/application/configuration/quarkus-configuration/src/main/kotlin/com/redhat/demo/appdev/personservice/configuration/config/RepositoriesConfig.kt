package com.redhat.demo.appdev.personservice.configuration.config

import com.redhat.demo.appdev.personservice.core.dataproviders.inmemory.person.InMemoryPersonRepository
import com.redhat.demo.appdev.personservice.core.usecases.person.PersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class RepositoriesConfig {

    @Produces
    @ApplicationScoped
    fun personRepository(): PersonRepository {
        return InMemoryPersonRepository()
    }
}