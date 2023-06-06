package com.redhat.demo.configuration.monolith.resources

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingResource {
    @GetMapping("/hello", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun hello(): String {
        return "Hello from Spring Web"
    }
}