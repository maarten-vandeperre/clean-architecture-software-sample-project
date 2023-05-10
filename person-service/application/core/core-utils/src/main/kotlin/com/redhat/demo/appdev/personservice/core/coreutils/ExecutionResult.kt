package com.redhat.demo.appdev.personservice.core.coreutils

class ExecutionResult<DATA_TYPE> private constructor(val result: ResultType<DATA_TYPE>) {

  companion object {
    fun <DATA_TYPE> success(data: DATA_TYPE): ExecutionResult<DATA_TYPE> {
      return ExecutionResult(SuccessResult(data))
    }
  }
}

abstract class ResultType<DATA_TYPE> {
}

class SuccessResult<DATA_TYPE>(val data: DATA_TYPE) : ResultType<DATA_TYPE>() {
}