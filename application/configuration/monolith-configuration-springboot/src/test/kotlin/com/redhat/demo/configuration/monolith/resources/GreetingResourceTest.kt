package com.redhat.demo.configuration.monolith.resources

import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(profiles = ["test"])
class GreetingResourceTest {
    @Test
    fun testHelloEndpoint() {
        RestAssured.given()
            .`when`()["/hello"]
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("Hello from RESTEasy Reactive"))
    }
}