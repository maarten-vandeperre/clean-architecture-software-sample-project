package com.redhat.demo.appdev.personservice.configuration.utils

import com.redhat.demo.appdev.personservice.core.coreutils.ExecutionResult
import com.redhat.demo.appdev.personservice.core.coreutils.SuccessResult
import java.net.URI
import java.util.*
import jakarta.ws.rs.core.Response

fun <DATA_TYPE : Any> ExecutionResult<DATA_TYPE>.toResponse(presenter: (data: DATA_TYPE) -> Any = { it }): Response {
  return if (this.result is SuccessResult<DATA_TYPE>) {
    Response.ok(presenter((this.result as SuccessResult<DATA_TYPE>).data)).build()
  } else {
    Response.serverError().build() // TODO
  }
}

fun ExecutionResult<UUID>.toCreatedResponse(presenter: (ref: UUID) -> URI): Response {
  return if (this.result is SuccessResult<UUID>) {
    Response.created(presenter((this.result as SuccessResult<UUID>).data)).build()
  } else {
    Response.serverError().build() // TODO
  }
}