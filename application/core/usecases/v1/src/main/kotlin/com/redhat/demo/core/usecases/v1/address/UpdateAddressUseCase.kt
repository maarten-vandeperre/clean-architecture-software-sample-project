package com.redhat.demo.core.usecases.v1.address

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import java.util.*

interface UpdateAddressUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?,
        val addressLine1: String?,
        val addressLine2: String?,
        val addressLine3: String?,
        val countryIsoCode: String?
    )

    data class Response(
        val ref: String
    )

    class ValidationException(message: String) : Exception(message)
    class NotFoundException(message: String) : Exception(message)
}

class DefaultUpdateAddressUseCase(
    private val addressRepository: AddressRepository
) : UpdateAddressUseCase {
    override fun execute(requestData: UpdateAddressUseCase.Request): UpdateAddressUseCase.Response {
        if (requestData.ref == null) {
            throw UpdateAddressUseCase.ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw UpdateAddressUseCase.ValidationException("Ref is not a UUID format")
            }
        }
        if (requestData.addressLine1 == null) {
            throw UpdateAddressUseCase.ValidationException("Address line 1 should not be null")
        }
        if (requestData.addressLine2 == null) {
            throw UpdateAddressUseCase.ValidationException("Address line 2 should not be null")
        }
        if (requestData.countryIsoCode == null) {
            throw UpdateAddressUseCase.ValidationException("Country code should not be null")
        }
        if (!addressRepository.exists(UUID.fromString(requestData.ref))) {
            throw UpdateAddressUseCase.NotFoundException("No address with ref is found")
        }
        return UpdateAddressUseCase.Response(
            addressRepository.save(
                AddressRepository.DbAddress(
                    ref = UUID.fromString(requestData.ref),
                    addressLine1 = requestData.addressLine1,
                    addressLine2 = requestData.addressLine2,
                    addressLine3 = requestData.addressLine3,
                    countryIsoCode = requestData.countryIsoCode
                )
            )
        )
    }

}