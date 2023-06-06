package com.redhat.demo.configuration.monolith.resources

import com.redhat.demo.core.domain.v1.ReadPerson
import com.redhat.demo.core.usecases.v1.person.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.websocket.server.PathParam
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonResource(
    private val createPersonUseCase: CreatePersonUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val deletePersonUseCase: DeletePersonUseCase,
    private val getPersonUseCase: GetPersonUseCase,
    private val searchPersonUseCase: SearchPeopleUseCase
) {
    @PostMapping("/api/people", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Create an person")
    @Tag(name = "PEOPLE_API")
    fun createPerson(data: RequestData): ResponseEntity<String> {
        try {
            return ResponseEntity.ok(
                createPersonUseCase.execute(
                    CreatePersonUseCase.Request(
                        firstName = data.firstName,
                        lastName = data.lastName,
                        birthDate = data.birthDate,
                        addressRef = data.addressRef,
                    )
                ).ref
            )
        } catch (e: CreatePersonUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }

    @PutMapping("/api/people/{ref}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Edit an person")
    @Tag(name = "PEOPLE_API")
    fun updatePerson(@PathParam("ref") ref: String, data: RequestData): ResponseEntity<String> {
        try {
            return ResponseEntity.ok(
                updatePersonUseCase.execute(
                    UpdatePersonUseCase.Request(
                        ref = ref,
                        firstName = data.firstName,
                        lastName = data.lastName,
                        birthDate = data.birthDate,
                        addressRef = data.addressRef
                    )
                ).ref
            )
        } catch (e: UpdatePersonUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        } catch (e: UpdatePersonUseCase.NotFoundException) {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/api/people/{ref}")
    @Operation(summary = "Delete an person")
    @Tag(name = "PEOPLE_API")
    fun deletePerson(@PathParam("ref") ref: String): ResponseEntity<Any> {
        try {
            deletePersonUseCase.execute(DeletePersonUseCase.Request(ref = ref))
            return ResponseEntity.ok().build()
        } catch (e: DeletePersonUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }

    @GetMapping("/api/people/{ref}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Get an individual person")
    @Tag(name = "PEOPLE_API")
    @ApiResponses(
        value = [
            ApiResponse(
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ReadPerson::class))
                ])
        ])
    fun getPerson(@PathParam("ref") ref: String): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(getPersonUseCase.execute(GetPersonUseCase.Request(ref = ref)).person)
        } catch (e: GetPersonUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        } catch (e: GetPersonUseCase.NotFoundException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/api/people", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Get all people")
    @Tag(name = "PEOPLE_API")
    @ApiResponses(
        value = [
            ApiResponse(
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = ReadPerson::class)))
                ])
        ])
    fun searchPeople(): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(searchPersonUseCase.execute(SearchPeopleUseCase.Request()).people)
        } catch (e: SearchPeopleUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }

    open class RequestData(
        var firstName: String?,
        var lastName: String?,
        var birthDate: String?,
        var addressRef: String?
    )
}