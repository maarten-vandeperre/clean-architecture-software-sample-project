package com.redhat.demo.configuration.monolith.resources

import io.quarkus.test.junit.QuarkusIntegrationTest

@QuarkusIntegrationTest
class GreetingResourceIT : GreetingResourceTest() { // Execute the same tests but in packaged mode.
}