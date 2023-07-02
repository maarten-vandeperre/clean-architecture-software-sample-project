package com.redhat.demo.configuration.monolith.resources

import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = ["test"])
class GreetingResourceTest {
    @LocalServerPort
    private var port: Int = 0
    @Test
    fun testHelloEndpoint() {
        RestAssured.given()
            .port(port)
            .`when`()["/hello"]
            .then()
            .statusCode(200)
            .body(CoreMatchers.`is`("Hello from Spring Web"))
    }
}