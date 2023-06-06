package com.redhat.demo.core.usecases.v1.person

import com.redhat.demo.core.domain.v1.ReadPerson
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import java.util.*

interface GetPersonUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    data class Request(
        val ref: String?
    )

    data class Response(
        val person: ReadPerson
    )

    class ValidationException(message: String) : Exception(message)
    class NotFoundException(message: String) : Exception(message)
}

class DefaultGetPersonUseCase(
    private val personRepository: PersonRepository
) : GetPersonUseCase {
    override fun execute(requestData: GetPersonUseCase.Request): GetPersonUseCase.Response {
        if (requestData.ref == null) {
            throw GetPersonUseCase.ValidationException("Ref should not be null")
        } else {
            try {
                UUID.fromString(requestData.ref)
            } catch (e: Exception) {
                throw GetPersonUseCase.ValidationException("Ref is not a UUID format")
            }
        }
        if (!personRepository.exists(UUID.fromString(requestData.ref))) {
            throw GetPersonUseCase.NotFoundException("No person with ref is found")
        }
        val dbPerson = personRepository.get(UUID.fromString(requestData.ref))!!
        return GetPersonUseCase.Response(
            ReadPerson(
                ref = dbPerson.ref.toString(),
                firstName = dbPerson.firstName,
                lastName = dbPerson.lastName,
                birthDate = dbPerson.birthDate
            )
        )
    }

}