package com.redhat.demo.infra.dataproviders.inmemory.repositories

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryAddressRepository: AddressRepository {
    private val db: MutableMap<UUID, AddressRepository.DbAddress> = ConcurrentHashMap<UUID, AddressRepository.DbAddress>()

    override fun save(address: AddressRepository.DbAddress): String {
        db[address.ref] = address
        return address.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        return db.contains(ref)
    }

    override fun delete(ref: UUID) {
        db.remove(ref)
    }

    override fun get(ref: UUID): AddressRepository.DbAddress {
        return db.get(ref)!!
    }

    override fun search(): List<AddressRepository.DbAddress> {
        return db.values.toList()
    }
}