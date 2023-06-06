package com.redhat.demo.core.usecases.v1.address

import com.redhat.demo.core.domain.v1.ReadAddress
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import java.util.*

interface GetAddressUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?
    )

    data class Response(
        val address: ReadAddress
    )

    class ValidationException(message: String) : Exception(message)
    class NotFoundException(message: String) : Exception(message)
}

class DefaultGetAddressUseCase(
    private val addressRepository: AddressRepository
) : GetAddressUseCase {
    override fun execute(requestData: GetAddressUseCase.Request): GetAddressUseCase.Response {
        if (requestData.ref == null) {
            throw GetAddressUseCase.ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw GetAddressUseCase.ValidationException("Ref is not a UUID format")
            }
        }
        if (!addressRepository.exists(UUID.fromString(requestData.ref))) {
            throw GetAddressUseCase.NotFoundException("No person with ref is found")
        }
        val dbAddress = addressRepository.get(UUID.fromString(requestData.ref))!!
        return GetAddressUseCase.Response(
            ReadAddress(
                ref = dbAddress.ref.toString(),
                addressLine1 = dbAddress.addressLine1,
                addressLine2 = dbAddress.addressLine2,
                addressLine3 = dbAddress.addressLine3,
                countryIsoCode = dbAddress.countryIsoCode
            )
        )
    }

}