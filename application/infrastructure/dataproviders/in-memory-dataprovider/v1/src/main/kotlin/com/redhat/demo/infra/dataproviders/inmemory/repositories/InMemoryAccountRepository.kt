package com.redhat.demo.infra.dataproviders.inmemory.repositories

import com.redhat.demo.core.usecases.repositories.v1.AccountRepository
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository

class InMemoryAccountRepository(
    private val addressRepository: AddressRepository,
    private val personRepository: PersonRepository
) : AccountRepository {
  override fun search(): List<AccountRepository.DbAccount> {
    val addresses = addressRepository.search().groupBy { it.ref }.toMap().mapValues { it.value[0] }
    return personRepository.search()
        .filter { it.addressRef != null }
        .filter { addresses.containsKey(it.addressRef!!) }
        .map { person ->
          val address = addresses[person.addressRef]!!
          AccountRepository.DbAccount(
              personRef = person.ref,
              firstName = person.firstName,
              lastName = person.lastName,
              birthDate = person.birthDate,
              addressRef = address.ref,
              addressLine1 = address.addressLine1,
              addressLine2 = address.addressLine2,
              addressLine3 = address.addressLine3,
              countryIsoCode = address.countryIsoCode
          )
        }
  }
}