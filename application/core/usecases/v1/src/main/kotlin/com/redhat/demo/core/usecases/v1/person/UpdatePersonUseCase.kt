package com.redhat.demo.core.usecases.v1.person

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.core.usecases.v1.person.UpdatePersonUseCase.ValidationException
import java.util.*

interface UpdatePersonUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?,
        val firstName: String?,
        val lastName: String?,
        val birthDate: String?,
        val addressRef: String?,
    )

    data class Response(
        val ref: String
    )

    class ValidationException(message: String) : Exception(message)
    class NotFoundException(message: String) : Exception(message)
}

class DefaultUpdatePersonUseCase(
    private val personRepository: PersonRepository
) : UpdatePersonUseCase {
    override fun execute(requestData: UpdatePersonUseCase.Request): UpdatePersonUseCase.Response {
        if (requestData.ref == null) {
            throw ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw ValidationException("Ref is not a UUID format")
            }
        }
        if (requestData.firstName == null) {
            throw ValidationException("First name should not be null")
        }
        if (requestData.lastName == null) {
            throw ValidationException("Last name should not be null")
        }
        if(!personRepository.exists(UUID.fromString(requestData.ref))){
            throw ValidationException("No person with ref is found")
        }
        if(requestData.addressRef != null){
            try {
                UUID.fromString(requestData.addressRef)
            } catch (e: Exception) {
                throw ValidationException("Address ref is not a UUID format")
            }
        }
        return UpdatePersonUseCase.Response(
            personRepository.save(
                PersonRepository.DbPerson(
                    ref = UUID.fromString(requestData.ref),
                    firstName = requestData.firstName,
                    lastName = requestData.lastName,
                    birthDate = requestData.birthDate,
                    addressRef = requestData.addressRef?.let { UUID.fromString(it) }
                )
            )
        )
    }

}