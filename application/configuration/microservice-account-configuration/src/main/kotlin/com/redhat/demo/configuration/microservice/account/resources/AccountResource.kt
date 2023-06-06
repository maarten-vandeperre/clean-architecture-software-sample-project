package com.redhat.demo.configuration.microservice.account.resources

import com.redhat.demo.configuration.microservice.account.services.AccountSyncDataService
import com.redhat.demo.core.usecases.v1.account.SearchAccountsUseCase
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import java.util.*

@Path("/api/accounts")
class AccountResource(
    private val searchAccountsUseCase: SearchAccountsUseCase,
    private val accountSyncDataService: AccountSyncDataService
) {

    @GET
    fun getAccounts(): Response {
        return Response.ok(
            searchAccountsUseCase.execute(SearchAccountsUseCase.Request()).accounts
        ).build()
    }

    @POST
    @Path("/monolith-data-changed/person-data/via-sink")
    @Operation(summary = "Create a notion that a person got updated/created/deleted via Knative Kafka Sink")
    @Tag(name = "PEOPLE_API")
    fun monolithPersonDataChangedViaSinkProcess(@Context httpHeaders: HttpHeaders, data: String): Response {
        println("person data changed via monolith: $data")
        accountSyncDataService.sync()
        return Response.ok().build()
    }

    @POST
    @Path("/monolith-data-changed/address-data/via-sink")
    @Operation(summary = "Create a notion that an address got updated/created/deleted via Knative Kafka Sink")
    @Tag(name = "PEOPLE_API")
    fun monolithAddressDataChangedViaSinkProcess(@Context httpHeaders: HttpHeaders, data: String): Response {
        println("address data changed via monolith: $data")
        accountSyncDataService.sync()
        return Response.ok().build()
    }

    @POST
    @Path("/data-change/person-data/via-channel")
    fun channelPersonDataChangedProcess(@Context httpHeaders: HttpHeaders, data: String): Response {
        println("person data changed via channel: $data")
        accountSyncDataService.sync()
        return Response.ok().build()
    }

    @POST
    @Path("/data-change/address-data/via-channel")
    fun channelAddressDataChangedProcess(@Context httpHeaders: HttpHeaders, data: String): Response {
        println("address data changed via channel: $data")
        accountSyncDataService.sync()
        return Response.ok().build()
    }

    @POST
    @Path("/data-change/address-data/via-broker")
    fun brokerAddressDataChangedProcess(@Context httpHeaders: HttpHeaders, data: String): Response {
        println("address data changed via broker: $data")
        accountSyncDataService.sync()
        return Response.ok().build()
    }

    @POST
    @Path("/data-change/person-data/via-broker")
    fun brokerPersonDataChangedProcess(@Context httpHeaders: HttpHeaders, data: String): Response {
        println("person data changed via broker: $data")
        accountSyncDataService.sync()
        return Response.ok().build()
    }

}