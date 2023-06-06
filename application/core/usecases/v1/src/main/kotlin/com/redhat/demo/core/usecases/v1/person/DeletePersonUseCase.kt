package com.redhat.demo.core.usecases.v1.person

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import java.util.*

interface DeletePersonUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?
    )

    class Response

    class ValidationException(message: String) : Exception(message)
}

class DefaultDeletePersonUseCase(
    private val personRepository: PersonRepository
) : DeletePersonUseCase {
    override fun execute(requestData: DeletePersonUseCase.Request): DeletePersonUseCase.Response {
        if (requestData.ref == null) {
            throw UpdatePersonUseCase.ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw UpdatePersonUseCase.ValidationException("Ref is not a UUID format")
            }
        }
        personRepository.delete(UUID.fromString(requestData.ref))
        return DeletePersonUseCase.Response()
    }

}