package com.redhat.demo.configuration.microservice.address.resources

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
    try {
      return Response.ok(
          createAddressUseCase.execute(
              CreateAddressUseCase.Request(
                  addressLine1 = data.addressLine1,
                  addressLine2 = data.addressLine2,
                  addressLine3 = data.addressLine3,
                  countryIsoCode = data.countryIsoCode
              )
          ).ref
      ).build()
    } catch (e: CreateAddressUseCase.ValidationException) {
      return Response.status(422, e.localizedMessage).build()
    }
  }

  @PUT
  @Path("/{ref}")
  @Consumes(value = [MediaType.APPLICATION_JSON])
  @Operation(summary = "Edit an address")
  @Tag(name = "ADDRESSES_API")
  fun updateAddress(@PathParam("ref") ref: String, data: RequestData): Response {
    try {
      return Response.ok(
          updateAddressUseCase.execute(
              UpdateAddressUseCase.Request(
                  ref = ref,
                  addressLine1 = data.addressLine1,
                  addressLine2 = data.addressLine2,
                  addressLine3 = data.addressLine3,
                  countryIsoCode = data.countryIsoCode
              )
          ).ref
      ).build()
    } catch (e: UpdateAddressUseCase.ValidationException) {
      return Response.status(422, e.localizedMessage).build()
    } catch (e: UpdateAddressUseCase.NotFoundException) {
      return Response.status(404, e.localizedMessage).build()
    }
  }

  @DELETE
  @Path("/{ref}")
  @Operation(summary = "Delete an address")
  @Tag(name = "ADDRESSES_API")
  fun deleteAddress(@PathParam("ref") ref: String): Response {
    try {
      deleteAddressUseCase.execute(DeleteAddressUseCase.Request(ref = ref))
      return Response.ok().build()
    } catch (e: DeleteAddressUseCase.ValidationException) {
      return Response.status(422, e.localizedMessage).build()
    }
  }

  @GET
  @Path("/{ref}")
  @Produces(value = [MediaType.APPLICATION_JSON])
  @Operation(summary = "Get an individual address")
  @Tag(name = "ADDRESSES_API")
  @APIResponseSchema(value = ReadAddress::class)
  fun getAddress(@PathParam("ref") ref: String): Response {
    try {
      return Response.ok(getAddressUseCase.execute(GetAddressUseCase.Request(ref = ref)).address).build()
    } catch (e: GetAddressUseCase.ValidationException) {
      return Response.status(422, e.localizedMessage).build()
    } catch (e: GetAddressUseCase.NotFoundException) {
      return Response.status(404, e.localizedMessage).build()
    }
  }

  @GET
  @Produces(value = [MediaType.APPLICATION_JSON])
  @Operation(summary = "Get all addresses")
  @Tag(name = "ADDRESSES_API")
  @APIResponseSchema(value = ReadAddress::class)
  fun searchAddresses(): Response {
    try {
      return Response.ok(searchAddressUseCase.execute(SearchAddressesUseCase.Request()).addresses).build()
    } catch (e: SearchAddressesUseCase.ValidationException) {
      return Response.status(422, e.localizedMessage).build()
    }
  }

  data class RequestData(
      val addressLine1: String?,
      val addressLine2: String?,
      val addressLine3: String?,
      val countryIsoCode: String?
  )
}