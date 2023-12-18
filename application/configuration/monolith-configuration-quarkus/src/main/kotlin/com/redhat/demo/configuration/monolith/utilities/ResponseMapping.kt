package com.redhat.demo.configuration.monolith.utilities

import com.redhat.demo.core.usecases.v1.address.UpdateAddressUseCase
import jakarta.ws.rs.core.Response

object ResponseMapping {
    fun <DATA_TYPE> mapToResponse(useCaseExecution: () -> DATA_TYPE): Response {
        return try {
            Response.ok(useCaseExecution()).build()
        } catch (e: UpdateAddressUseCase.ValidationException) {
            Response.status(422, e.localizedMessage).build()
        } catch (e: UpdateAddressUseCase.NotFoundException) {
            Response.status(404, e.localizedMessage).build()
        }
    }

    fun mapToNoDataResponse(useCaseExecution: () -> Any): Response {
        return try {
            useCaseExecution()
            Response.noContent().build()
        } catch (e: UpdateAddressUseCase.ValidationException) {
            Response.status(422, e.localizedMessage).build()
        } catch (e: UpdateAddressUseCase.NotFoundException) {
            Response.status(404, e.localizedMessage).build()
        }
    }
}