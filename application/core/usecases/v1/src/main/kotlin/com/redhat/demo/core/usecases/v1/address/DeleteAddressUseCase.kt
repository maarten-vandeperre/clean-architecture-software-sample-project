package com.redhat.demo.core.usecases.v1.address

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import java.util.*

interface DeleteAddressUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?
    )

    class Response

    class ValidationException(message: String) : Exception(message)
}

class DefaultDeleteAddressUseCase(
    private val addressRepository: AddressRepository
) : DeleteAddressUseCase {
    override fun execute(requestData: DeleteAddressUseCase.Request): DeleteAddressUseCase.Response {
        if (requestData.ref == null) {
            throw UpdateAddressUseCase.ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw UpdateAddressUseCase.ValidationException("Ref is not a UUID format")
            }
        }
        addressRepository.delete(UUID.fromString(requestData.ref))
        return DeleteAddressUseCase.Response()
    }

}