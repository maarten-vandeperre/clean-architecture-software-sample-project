package com.redhat.demo.appdev.personservice.configuration

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer


@SpringBootApplication
open class PersonServiceApplication: SpringBootServletInitializer() {
  override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
    return application.sources(PersonServiceApplication::class.java)
  }

  companion object{
    @JvmStatic
    fun main(args: Array<String>) {
      SpringApplication.run(PersonServiceApplication::class.java, *args)
    }
  }
}

