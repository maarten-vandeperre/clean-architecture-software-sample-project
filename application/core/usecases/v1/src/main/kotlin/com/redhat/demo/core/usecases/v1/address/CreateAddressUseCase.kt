package com.redhat.demo.core.usecases.v1.address

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import java.util.*

interface CreateAddressUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val addressLine1: String?,
        val addressLine2: String?,
        val addressLine3: String?,
        val countryIsoCode: String?
    )

    data class Response(
        val ref: String
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultCreateAddressUseCase(
    private val addressRepository: AddressRepository
) : CreateAddressUseCase {
    override fun execute(requestData: CreateAddressUseCase.Request): CreateAddressUseCase.Response {
        if (requestData.addressLine1 == null) {
            throw CreateAddressUseCase.ValidationException("Address line 1 should not be null")
        }
        if (requestData.addressLine2 == null) {
            throw CreateAddressUseCase.ValidationException("Address line 2 should not be null")
        }
        if (requestData.countryIsoCode == null) {
            throw CreateAddressUseCase.ValidationException("Country code should not be null")
        }
        return CreateAddressUseCase.Response(
            addressRepository.save(
                AddressRepository.DbAddress(
                    ref = UUID.randomUUID(),
                    addressLine1 = requestData.addressLine1,
                    addressLine2 = requestData.addressLine2,
                    addressLine3 = requestData.addressLine3,
                    countryIsoCode = requestData.countryIsoCode
                )
            )
        )
    }

}