package com.redhat.demo.configuration.monolith.resources

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/hello")
class GreetingResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String {
        return "Hello from RESTEasy Reactive"
    }
}