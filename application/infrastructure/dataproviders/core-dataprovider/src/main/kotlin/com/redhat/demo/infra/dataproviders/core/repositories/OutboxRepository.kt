package com.redhat.demo.infra.dataproviders.core.repositories

import com.redhat.demo.infra.dataproviders.core.domain.OutboxEvent

interface OutboxRepository {
  fun save(event: OutboxEvent)
}