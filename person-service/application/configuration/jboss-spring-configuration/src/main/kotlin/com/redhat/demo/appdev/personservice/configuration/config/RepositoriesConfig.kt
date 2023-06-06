package com.redhat.demo.appdev.personservice.configuration.config

import com.redhat.demo.appdev.personservice.core.dataproviders.inmemory.person.InMemoryPersonRepository
import com.redhat.demo.appdev.personservice.core.usecases.person.PersonRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class RepositoriesConfig {//Quarkus VS Spring ==> class needs to be open over here

    @Bean
    open fun personRepository(): PersonRepository {//Quarkus VS Spring ==> method needs to be open over here
        return InMemoryPersonRepository()
    }
}