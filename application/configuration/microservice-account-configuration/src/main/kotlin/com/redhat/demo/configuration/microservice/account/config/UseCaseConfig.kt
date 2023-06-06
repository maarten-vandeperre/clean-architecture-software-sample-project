package com.redhat.demo.configuration.microservice.account.config

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
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class UseCaseConfig {

  @Produces
  fun searchAccountsUseCase(accountRepository: AccountRepository): SearchAccountsUseCase {
    return DefaultSearchAccountsUseCase(accountRepository)
  }
}