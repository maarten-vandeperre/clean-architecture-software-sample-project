package com.redhat.demo.appdev.personservice.configuration.person

import com.redhat.demo.appdev.personservice.configuration.PersonServiceApplication.Tags.PEOPLE_API
import com.redhat.demo.appdev.personservice.configuration.utils.toCreatedResponse
import com.redhat.demo.appdev.personservice.configuration.utils.toResponse
import com.redhat.demo.appdev.personservice.core.usecases.person.AddPersonUseCase
import com.redhat.demo.appdev.personservice.core.usecases.person.GetPeopleUseCase
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.net.URI
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response


@Path("/people")
class PersonResource(
        private val getPeopleUseCase: GetPeopleUseCase,
        private val addPersonUseCase: AddPersonUseCase
) {
  @GET
  @Path("/v1")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get people (V1)")
  @Tag(name = PEOPLE_API)
  @APIResponseSchema(value = V1PersonData::class)
  fun getPeopleV1(): Response {
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

  @GET
  @Path("/v2")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get people (V2)")
  @Tag(name = PEOPLE_API)
  @APIResponseSchema(value = V2PersonData::class)
  fun getPeopleV2(): Response {
    return getPeopleUseCase.execute().toResponse { data ->
      data.map { p ->
        mapOf(
                "ref" to p.ref,
                "firstName" to p.firstName,
                "lastName" to p.lastName,
                "birthDate" to "${p.birthDate.day}/${p.birthDate.month}/${p.birthDate.year}",
                "address" to "${p.address.addressLine1}, ${p.address.addressLine2}, ${p.address.country}"
        )
      }
    }
  }

  @GET
  @Path("/v2/{ref}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Get people (V2)")
  @Tag(name = PEOPLE_API)
  @APIResponseSchema(value = V2PersonData::class)
  fun getPersonV2(@PathParam("ref") ref: String): Response {
    return getPeopleUseCase.execute().toResponse { data -> //TODO to use case
      data
              .filter { it.ref.toString() == ref }
              .map { p ->
                mapOf(
                        "ref" to p.ref,
                        "firstName" to p.firstName,
                        "lastName" to p.lastName,
                        "birthDate" to "${p.birthDate.day}/${p.birthDate.month}/${p.birthDate.year}",
                        "address" to "${p.address.addressLine1}, ${p.address.addressLine2}, ${p.address.country}"
                )
              }
              .first()
    }
  }

  @POST
  @Path("/v2")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Operation(summary = "Create person entity")
  @Tag(name = PEOPLE_API)
  @APIResponseSchema(value = V2PersonData::class)
  fun addPersonV2(requestData: AddPersonUseCase.Request): Response { //TODO split request class into a resource one and the use case one
    return addPersonUseCase
            .execute(requestData)
            .toCreatedResponse { data -> URI("/people/v2/${data.toString()}") }
  }

  @Schema(
          name = "V1PersonData",
          description = "Person data (V1)."
  )
  internal data class V1PersonData(
          val ref: String,
          val firstName: String,
          val lastName: String
  )

  @Schema(
          name = "V2PersonData",
          description = "Person data (V2)."
  )
  internal data class V2PersonData(
          val ref: String,
          val firstName: String,
          val birthDate: String,
          val lastName: String,
          val address: String
  )
}