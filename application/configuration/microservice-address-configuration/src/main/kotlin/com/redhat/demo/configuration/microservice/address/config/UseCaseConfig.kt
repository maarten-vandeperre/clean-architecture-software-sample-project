package com.redhat.demo.configuration.microservice.address.config

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.v1.address.*
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class UseCaseConfig {

    @Produces
    fun createAddressUseCase(addressRepository: AddressRepository): CreateAddressUseCase {
        return DefaultCreateAddressUseCase(addressRepository)
    }

    @Produces
    fun updateAddressUseCase(addressRepository: AddressRepository): UpdateAddressUseCase {
        return DefaultUpdateAddressUseCase(addressRepository)
    }

    @Produces
    fun deleteAddressUseCase(addressRepository: AddressRepository): DeleteAddressUseCase {
        return DefaultDeleteAddressUseCase(addressRepository)
    }

    @Produces
    fun getAddressUseCase(addressRepository: AddressRepository): GetAddressUseCase {
        return DefaultGetAddressUseCase(addressRepository)
    }

    @Produces
    fun searchAddressesUseCase(addressRepository: AddressRepository): SearchAddressesUseCase {
        return DefaultSearchAddressUseCase(addressRepository)
    }

}