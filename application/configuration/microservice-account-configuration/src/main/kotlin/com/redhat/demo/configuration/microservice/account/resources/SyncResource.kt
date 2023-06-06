package com.redhat.demo.configuration.microservice.account.resources

import com.redhat.demo.configuration.microservice.account.config.Mongo
import com.redhat.demo.configuration.microservice.account.config.Postgres
import com.redhat.demo.configuration.microservice.account.services.AccountSyncDataService
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Response

@Path("/sync-account-data")
class SyncResource(
    private val accountSyncDataService: AccountSyncDataService
) {

    @GET
    fun sync(): Response {
        accountSyncDataService.sync()
        return Response.ok().build()
    }
}
