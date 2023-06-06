package com.redhat.demo.appdev.personservice.configuration

import com.redhat.demo.appdev.personservice.configuration.PersonServiceApplication.Tags
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Contact
import org.eclipse.microprofile.openapi.annotations.info.Info
import org.eclipse.microprofile.openapi.annotations.info.License
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import jakarta.ws.rs.core.Application

@OpenAPIDefinition(
        tags = [
          Tag(name = Tags.PEOPLE_API, description = "People API documentation."),
          Tag(name = Tags.TEST_API, description = "Documentation just in sake of demoing namespaces")
        ],
        info = Info(
                title = "Person Service API",
                version = "1.0.0",
                contact = Contact(
                        name = "Person service API Support",
                        email = "mvandepe+person_service_support@redhat.com"),
                license = License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
class PersonServiceApplication : Application() {
  object Tags {
    const val PEOPLE_API = "PEOPLE_API"
    const val TEST_API = "TEST_API"
  }
}
