package com.redhat.demo.infra.dataproviders.inmemory.repositories

import com.redhat.demo.infra.dataproviders.core.domain.OutboxEvent
import com.redhat.demo.infra.dataproviders.core.repositories.OutboxRepository

class InMemoryOutboxRepository : OutboxRepository {

    override fun save(event: OutboxEvent) {
        db.add(event)
    }

    fun getEvents(): List<OutboxEvent> {
        return db
    }

    companion object {
        private val db: MutableList<OutboxEvent> = mutableListOf()
    }
}