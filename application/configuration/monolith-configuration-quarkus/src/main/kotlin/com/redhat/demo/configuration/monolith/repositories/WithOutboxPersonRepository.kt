package com.redhat.demo.configuration.monolith.repositories

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.core.domain.OutboxEvent
import com.redhat.demo.infra.dataproviders.core.domain.OutboxEventAction
import com.redhat.demo.infra.dataproviders.core.repositories.OutboxRepository
import jakarta.transaction.Transactional
import java.time.LocalDateTime
import java.util.*

class WithOutboxPersonRepository(
    private val personRepository: PersonRepository,
    private val outboxRepository: OutboxRepository
) : PersonRepository {
    @Transactional
    override fun save(person: PersonRepository.DbPerson): String {
        val result = if (personRepository.exists(person.ref)) {
            val result = personRepository.save(person)
            outboxRepository.save(OutboxEvent(person.ref, OutboxEventAction.UPDATED, LocalDateTime.now()))
            result
        } else {
            val result = personRepository.save(person)
            outboxRepository.save(OutboxEvent(person.ref, OutboxEventAction.CREATED, LocalDateTime.now()))
            result
        }
        return result
    }

    override fun exists(ref: UUID): Boolean {
        return personRepository.exists(ref)
    }

    @Transactional
    override fun delete(ref: UUID) {
        personRepository.delete(ref)
        outboxRepository.save(OutboxEvent(ref, OutboxEventAction.DELETED, LocalDateTime.now()))
    }

    override fun get(ref: UUID): PersonRepository.DbPerson? {
        return personRepository.get(ref)
    }

    override fun search(): List<PersonRepository.DbPerson> {
        return personRepository.search()
    }
}