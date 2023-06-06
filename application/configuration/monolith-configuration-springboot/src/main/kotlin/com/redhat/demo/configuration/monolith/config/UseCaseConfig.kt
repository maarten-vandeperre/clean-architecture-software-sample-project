package com.redhat.demo.configuration.monolith.config

import com.redhat.demo.core.usecases.repositories.v1.AccountRepository
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.v1.account.DefaultSearchAccountsUseCase
import com.redhat.demo.core.usecases.v1.account.SearchAccountsUseCase
import com.redhat.demo.core.usecases.v1.address.CreateAddressUseCase
import com.redhat.demo.core.usecases.v1.address.DefaultCreateAddressUseCase
import com.redhat.demo.core.usecases.v1.address.DefaultDeleteAddressUseCase
import com.redhat.demo.core.usecases.v1.address.DefaultGetAddressUseCase
import com.redhat.demo.core.usecases.v1.address.DefaultSearchAddressUseCase
import com.redhat.demo.core.usecases.v1.address.DefaultUpdateAddressUseCase
import com.redhat.demo.core.usecases.v1.address.DeleteAddressUseCase
import com.redhat.demo.core.usecases.v1.address.GetAddressUseCase
import com.redhat.demo.core.usecases.v1.address.SearchAddressesUseCase
import com.redhat.demo.core.usecases.v1.address.UpdateAddressUseCase
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
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class UseCaseConfig { //FIXME to support Spring, we need to mark the configuration class "open"

    @Bean
    open fun createPersonUseCase(personRepository: PersonRepository): CreatePersonUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultCreatePersonUseCase(personRepository)
    }

    @Bean
    open fun updatePersonUseCase(personRepository: PersonRepository): UpdatePersonUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultUpdatePersonUseCase(personRepository)
    }

    @Bean
    open fun deletePersonUseCase(personRepository: PersonRepository): DeletePersonUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultDeletePersonUseCase(personRepository)
    }

    @Bean
    open fun getPersonUseCase(personRepository: PersonRepository): GetPersonUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultGetPersonUseCase(personRepository)
    }

    @Bean
    open fun searchPeopleUseCase(personRepository: PersonRepository): SearchPeopleUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultSearchPeopleUseCase(personRepository)
    }

    @Bean
    open fun createAddressUseCase(addressRepository: AddressRepository): CreateAddressUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultCreateAddressUseCase(addressRepository)
    }

    @Bean
    open fun updateAddressUseCase(addressRepository: AddressRepository): UpdateAddressUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultUpdateAddressUseCase(addressRepository)
    }

    @Bean
    open fun deleteAddressUseCase(addressRepository: AddressRepository): DeleteAddressUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultDeleteAddressUseCase(addressRepository)
    }

    @Bean
    open fun getAddressUseCase(addressRepository: AddressRepository): GetAddressUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultGetAddressUseCase(addressRepository)
    }

    @Bean
    open fun searchAddressesUseCase(addressRepository: AddressRepository): SearchAddressesUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultSearchAddressUseCase(addressRepository)
    }

    @Bean
    open fun searchAccountsUseCase(accountRepository: AccountRepository): SearchAccountsUseCase {
        //FIXME to support Spring, we need to mark the bean method "open"
        //FIXME repository config not found in intellij
        return DefaultSearchAccountsUseCase(accountRepository)
    }
}