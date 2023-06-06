package com.redhat.demo.configuration.monolith.repositories

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.infra.dataproviders.core.domain.OutboxEvent
import com.redhat.demo.infra.dataproviders.core.domain.OutboxEventAction
import com.redhat.demo.infra.dataproviders.core.repositories.OutboxRepository
import jakarta.transaction.Transactional
import java.time.LocalDateTime
import java.util.*

class WithOutboxAddressRepository(
    private val addressRepository: AddressRepository,
    private val outboxRepository: OutboxRepository
) : AddressRepository {
    @Transactional
    override fun save(address: AddressRepository.DbAddress): String {
        val result = if (addressRepository.exists(address.ref)) {
            val result = addressRepository.save(address)
            outboxRepository.save(OutboxEvent(address.ref, OutboxEventAction.UPDATED, LocalDateTime.now()))
            result
        } else {
            val result = addressRepository.save(address)
            outboxRepository.save(OutboxEvent(address.ref, OutboxEventAction.CREATED, LocalDateTime.now()))
            result
        }
        return result
    }

    override fun exists(ref: UUID): Boolean {
        return addressRepository.exists(ref)
    }

    @Transactional
    override fun delete(ref: UUID) {
        addressRepository.delete(ref)
        outboxRepository.save(OutboxEvent(ref, OutboxEventAction.DELETED, LocalDateTime.now()))
    }

    override fun get(ref: UUID): AddressRepository.DbAddress? {
        return addressRepository.get(ref)
    }

    override fun search(): List<AddressRepository.DbAddress> {
        return addressRepository.search()
    }
}