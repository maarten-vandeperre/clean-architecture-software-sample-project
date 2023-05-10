package com.redhat.demo.appdev.personservice.configuration.person

import com.redhat.demo.appdev.personservice.configuration.PersonServiceApplication.Tags.TEST_API
import com.redhat.demo.appdev.personservice.configuration.utils.toResponse
import com.redhat.demo.appdev.personservice.core.usecases.person.GetPeopleUseCase
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response


@Path("/tests")
class TestResource(
        private val getPeopleUseCase: GetPeopleUseCase
) {
  @GET
  @Path("/v1")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get test (V1)")
  @Tag(name = TEST_API)
  fun getPeopleV1(): Response {
    return getPeopleUseCase.execute().toResponse { data ->
      data.map { p ->
        mapOf(
                "firstName" to p.firstName,
                "lastName" to p.lastName,
        )
      }
    }
  }

  @GET
  @Path("/v2")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get test (V2)")
  @Tag(name = TEST_API)
  fun getPeopleV2(): Response {
    return getPeopleUseCase.execute().toResponse { data ->
      data.map { p ->
        mapOf(
                "firstName" to p.firstName,
                "lastName" to p.lastName,
                "birthDate" to "${p.birthDate.day}/${p.birthDate.month}/${p.birthDate.year}",
                "address" to "${p.address.addressLine1}, ${p.address.addressLine2}, ${p.address.country}"
        )
      }
    }
  }
}