package com.redhat.demo.infra.dataproviders.core.domain

import java.time.LocalDateTime
import java.util.*

data class OutboxEvent(
    val dataRef: UUID,
    val action: OutboxEventAction,
    val timestamp: LocalDateTime
)

enum class OutboxEventAction {
    CREATED, UPDATED, DELETED
}