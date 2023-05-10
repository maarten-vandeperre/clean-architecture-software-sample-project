package com.redhat.demo.appdev.personservice.configuration.person

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test

@QuarkusTest
class PersonResourceTest {
    @Test
    fun testGetPeopleV2Endpoint() {
        RestAssured.given()
                .`when`()["/people/v2"]
                .then()
                .statusCode(200)
                .body(CoreMatchers.containsString("Maarten"))
    }
}