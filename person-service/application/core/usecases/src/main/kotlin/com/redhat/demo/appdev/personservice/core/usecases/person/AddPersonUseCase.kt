package com.redhat.demo.appdev.personservice.core.usecases.person

import com.redhat.demo.appdev.personservice.core.coreutils.ExecutionResult
import com.redhat.demo.appdev.personservice.core.domain.address.Address
import com.redhat.demo.appdev.personservice.core.domain.address.Country
import com.redhat.demo.appdev.personservice.core.domain.common.Date
import com.redhat.demo.appdev.personservice.core.domain.person.Person
import com.redhat.demo.appdev.personservice.core.domain.person.PersonRef
import com.redhat.demo.appdev.personservice.core.usecases.person.AddPersonUseCase.Request
import java.util.*

interface AddPersonUseCase {
  fun execute(request: Request): ExecutionResult<PersonRef>

  class Request(
          val firstName: String?,
          val lastName: String?,
          val birthDateDay: Int?,
          val birthDateMonth: Int?,
          val birthDateYear: Int?,
          val addressLine1: String?,
          val addressLine2: String?,
          val countryIsoCode: String?
  )
}

internal class DefaultAddPersonUseCase(
        private val personRepository: PersonRepository
) : AddPersonUseCase {
  override fun execute(request: Request): ExecutionResult<PersonRef> {
    //TODO add validation
    val countryIsoCode = Country.BE
    return personRepository.addPerson(
            Person(
                    ref = UUID.randomUUID(),
                    firstName = request.firstName!!,
                    lastName = request.lastName!!,
                    birthDate = Date(day = request.birthDateDay!!, month = request.birthDateMonth!!, year = request.birthDateYear!!),
                    address = Address(ref = UUID.randomUUID(), addressLine1 = request.addressLine1!!, addressLine2 = request.addressLine2!!, country = countryIsoCode)
            )
    )
  }

}