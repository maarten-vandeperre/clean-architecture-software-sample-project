package com.redhat.demo.appdev.personservice.configuration.person

import com.redhat.demo.appdev.personservice.configuration.utils.toResponse
import com.redhat.demo.appdev.personservice.core.usecases.person.AddPersonUseCase
import com.redhat.demo.appdev.personservice.core.usecases.person.GetPeopleUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonResource(
        private val getPeopleUseCase: GetPeopleUseCase
) {
  @GetMapping("/people/v1")
  fun getPeopleV1(): ResponseEntity<Any> {
    return getPeopleUseCase.execute().toResponse { data ->
      data.map { p ->
        mapOf(
                "ref" to p.ref,
                "firstName" to p.firstName,
                "lastName" to p.lastName,
        )
      }
    }
  }
}