package com.redhat.demo.appdev.personservice.configuration.utils

import com.redhat.demo.appdev.personservice.core.coreutils.ExecutionResult
import com.redhat.demo.appdev.personservice.core.coreutils.SuccessResult
import org.springframework.http.ResponseEntity
import java.net.URI
import java.util.*

fun <DATA_TYPE : Any> ExecutionResult<DATA_TYPE>.toResponse(presenter: (data: DATA_TYPE) -> Any = { it }): ResponseEntity<Any> {
  return if (this.result is SuccessResult<DATA_TYPE>) {
    ResponseEntity.ok(presenter((this.result as SuccessResult<DATA_TYPE>).data))
  } else {
    ResponseEntity.internalServerError().build() // TODO
  }
}

fun ExecutionResult<UUID>.toCreatedResponse(presenter: (ref: UUID) -> URI): ResponseEntity<Any> {
  return if (this.result is SuccessResult<UUID>) {
    ResponseEntity.created(presenter((this.result as SuccessResult<UUID>).data)).build()
  } else {
    ResponseEntity.internalServerError().build() // TODO
  }
}