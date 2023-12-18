package com.redhat.demo.configuration.monolith.resources

import com.redhat.demo.configuration.monolith.utilities.ResponseMapping.mapToNoDataResponse
import com.redhat.demo.configuration.monolith.utilities.ResponseMapping.mapToResponse
import com.redhat.demo.core.domain.v1.ReadPerson
import com.redhat.demo.core.usecases.v1.person.*
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/people")
class PersonResource(
    private val createPersonUseCase: CreatePersonUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val deletePersonUseCase: DeletePersonUseCase,
    private val getPersonUseCase: GetPersonUseCase,
    private val searchPeopleUseCase: SearchPeopleUseCase
) {
    @POST
    @Consumes(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Create person")
    @Tag(name = "PEOPLE_API")
    fun createPerson(data: RequestData): Response {
        return mapToResponse {
            createPersonUseCase.execute(
                CreatePersonUseCase.Request(
                    firstName = data.firstName,
                    lastName = data.lastName,
                    birthDate = data.birthDate,
                    addressRef = data.addressRef,
                )
            ).ref
        }
    }

    @PUT
    @Path("/{ref}")
    @Consumes(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Update person")
    @Tag(name = "PEOPLE_API")
    fun updatePerson(@PathParam("ref") ref: String, data: RequestData): Response {
        return mapToResponse {
            updatePersonUseCase.execute(
                UpdatePersonUseCase.Request(
                    ref = ref,
                    firstName = data.firstName,
                    lastName = data.lastName,
                    birthDate = data.birthDate,
                    addressRef = data.addressRef
                )
            ).ref
        }
    }

    @DELETE
    @Path("/{ref}")
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Delete person")
    @Tag(name = "PEOPLE_API")
    fun deletePerson(@PathParam("ref") ref: String): Response {
        return mapToNoDataResponse {
            deletePersonUseCase.execute(DeletePersonUseCase.Request(ref = ref))
        }
    }

    @GET
    @Path("/{ref}")
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Get an individual person")
    @Tag(name = "PEOPLE_API")
    @APIResponseSchema(value = ReadPerson::class)
    fun getPerson(@PathParam("ref") ref: String): Response {
        return mapToResponse {
            getPersonUseCase.execute(GetPersonUseCase.Request(ref = ref)).person
        }
    }

    @GET
    @Operation(summary = "Get all people")
    @Tag(name = "PEOPLE_API")
    @APIResponseSchema(value = ReadPerson::class)
    fun searchPeople(): Response {
        return mapToResponse {
            searchPeopleUseCase.execute(SearchPeopleUseCase.Request()).people
        }
    }

    open class RequestData(
        var firstName: String?,
        var lastName: String?,
        var birthDate: String?,
        var addressRef: String?
    )
}