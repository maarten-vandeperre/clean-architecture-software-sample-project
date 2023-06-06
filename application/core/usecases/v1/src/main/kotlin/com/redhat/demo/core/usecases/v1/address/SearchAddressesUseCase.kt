package com.redhat.demo.core.usecases.v1.address

import com.redhat.demo.core.domain.v1.ReadAddress
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import java.util.*

interface SearchAddressesUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    class Request

    data class Response(
        val addresses: List<ReadAddress>
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultSearchAddressUseCase(
    private val addressRepository: AddressRepository
) : SearchAddressesUseCase {
    override fun execute(requestData: SearchAddressesUseCase.Request): SearchAddressesUseCase.Response {
        return SearchAddressesUseCase.Response(
            addressRepository.search().map {
                ReadAddress(
                    ref = it.ref.toString(),
                    addressLine1 = it.addressLine1,
                    addressLine2 = it.addressLine2,
                    addressLine3 = it.addressLine3,
                    countryIsoCode = it.countryIsoCode
                )
            }
        )
    }

}