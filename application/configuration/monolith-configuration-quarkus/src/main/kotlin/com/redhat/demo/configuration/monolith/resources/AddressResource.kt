package com.redhat.demo.configuration.monolith.resources

import com.redhat.demo.configuration.monolith.utilities.ResponseMapping.mapToNoDataResponse
import com.redhat.demo.configuration.monolith.utilities.ResponseMapping.mapToResponse
import com.redhat.demo.core.domain.v1.ReadAddress
import com.redhat.demo.core.usecases.v1.address.*
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Path("/api/addresses")
class AddressResource(
    private val createAddressUseCase: CreateAddressUseCase,
    private val updateAddressUseCase: UpdateAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val searchAddressUseCase: SearchAddressesUseCase
) {
    @POST
    @Consumes(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Create an address")
    @Tag(name = "ADDRESSES_API")
    fun createAddress(data: RequestData): Response {
        return mapToResponse {
            createAddressUseCase.execute(
                CreateAddressUseCase.Request(
                    addressLine1 = data.addressLine1,
                    addressLine2 = data.addressLine2,
                    addressLine3 = data.addressLine3,
                    countryIsoCode = data.countryIsoCode
                )
            ).ref
        }
    }

    @PUT
    @Path("/{ref}")
    @Consumes(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Edit an address")
    @Tag(name = "ADDRESSES_API")
    fun updateAddress(@PathParam("ref") ref: String, data: RequestData): Response {
        return mapToResponse {
            updateAddressUseCase.execute(
                UpdateAddressUseCase.Request(
                    ref = ref,
                    addressLine1 = data.addressLine1,
                    addressLine2 = data.addressLine2,
                    addressLine3 = data.addressLine3,
                    countryIsoCode = data.countryIsoCode
                )
            ).ref
        }
    }

    @DELETE
    @Path("/{ref}")
    @Operation(summary = "Delete an address")
    @Tag(name = "ADDRESSES_API")
    fun deleteAddress(@PathParam("ref") ref: String): Response {
        return mapToNoDataResponse {
            deleteAddressUseCase.execute(DeleteAddressUseCase.Request(ref = ref))
        }
    }

    @GET
    @Path("/{ref}")
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Get an individual address")
    @Tag(name = "ADDRESSES_API")
    @APIResponseSchema(value = ReadAddress::class)
    fun getAddress(@PathParam("ref") ref: String): Response {
        return mapToResponse {
            getAddressUseCase.execute(GetAddressUseCase.Request(ref = ref)).address
        }
    }

    @GET
    @Produces(value = [MediaType.APPLICATION_JSON])
    @Operation(summary = "Get all addresses")
    @Tag(name = "ADDRESSES_API")
    @APIResponseSchema(value = ReadAddress::class)
    fun searchAddresses(): Response {
        return mapToResponse {
            searchAddressUseCase.execute(SearchAddressesUseCase.Request()).addresses
        }
    }

    data class RequestData(
        val addressLine1: String?,
        val addressLine2: String?,
        val addressLine3: String?,
        val countryIsoCode: String?
    )
}