package com.redhat.demo.configuration.monolith

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@OpenAPIDefinition(
    tags = [
        Tag(name = "PEOPLE_API", description = "People API documentation."),
        Tag(name = "ADDRESSES_API", description = "Addresses API documentation."),
    ],
    info = Info(
        title = "Accounts, People & addresses Service API - Monolith",
        version = "1.0.0",
        contact = Contact(
            name = "API Support",
            email = "mvandepe+support@redhat.com"
        ),
        license = License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"
        )
    )
)
@SpringBootApplication
open class MonolithApplication { //FIXME this class should only be open to enable Spring....
}

fun main(args: Array<String>) {
    runApplication<MonolithApplication>(*args)
}
