package com.redhat.demo.configuration.monolith.resources

import com.redhat.demo.core.domain.v1.ReadAddress
import com.redhat.demo.core.usecases.v1.address.*
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
class AddressResource(
    private val createAddressUseCase: CreateAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val searchAddressUseCase: SearchAddressesUseCase
) {
    @PostMapping("/api/addresses", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Create an address")
    @Tag(name = "ADDRESSES_API")
    fun createAddress(data: RequestData): ResponseEntity<String> {
        try {
            return ResponseEntity.ok(
                createAddressUseCase.execute(
                    CreateAddressUseCase.Request(
                        addressLine1 = data.addressLine1,
                        addressLine2 = data.addressLine2,
                        addressLine3 = data.addressLine3,
                        countryIsoCode = data.countryIsoCode
                    )
                ).ref
            )
        } catch (e: CreateAddressUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }

    @PutMapping("/api/addresses/{ref}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Edit an address")
    @Tag(name = "ADDRESSES_API")
    fun updateAddress(@PathParam("ref") ref: String, data: RequestData): ResponseEntity<String> {
        try {
            return ResponseEntity.ok(
                updateAddressUseCase.execute(
                    UpdateAddressUseCase.Request(
                        ref = ref,
                        addressLine1 = data.addressLine1,
                        addressLine2 = data.addressLine2,
                        addressLine3 = data.addressLine3,
                        countryIsoCode = data.countryIsoCode
                    )
                ).ref
            )
        } catch (e: UpdateAddressUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        } catch (e: UpdateAddressUseCase.NotFoundException) {
            return ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/api/addresses/{ref}")
    @Operation(summary = "Delete an address")
    @Tag(name = "ADDRESSES_API")
    fun deleteAddress(@PathParam("ref") ref: String): ResponseEntity<Any> {
        try {
            deleteAddressUseCase.execute(DeleteAddressUseCase.Request(ref = ref))
            return ResponseEntity.ok().build()
        } catch (e: DeleteAddressUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }

    @GetMapping("/api/addresses/{ref}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Get an individual address")
    @Tag(name = "ADDRESSES_API")
    @ApiResponses(
        value = [
            ApiResponse(
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = Schema(implementation = ReadAddress::class))
                ])
        ])
    fun getAddress(@PathParam("ref") ref: String): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(getAddressUseCase.execute(GetAddressUseCase.Request(ref = ref)).address)
        } catch (e: GetAddressUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        } catch (e: GetAddressUseCase.NotFoundException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/api/addresses", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Get all addresses")
    @Tag(name = "ADDRESSES_API")
    @ApiResponses(
        value = [
            ApiResponse(
                content = [
                    Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = ArraySchema(schema = Schema(implementation = ReadAddress::class)))
                ])
        ])
    fun searchAddresses(): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(searchAddressUseCase.execute(SearchAddressesUseCase.Request()).addresses)
        } catch (e: SearchAddressesUseCase.ValidationException) {
            return ResponseEntity.badRequest().body(e.localizedMessage)
        }
    }

    data class RequestData(
        val addressLine1: String?,
        val addressLine2: String?,
        val addressLine3: String?,
        val countryIsoCode: String?
    )
}