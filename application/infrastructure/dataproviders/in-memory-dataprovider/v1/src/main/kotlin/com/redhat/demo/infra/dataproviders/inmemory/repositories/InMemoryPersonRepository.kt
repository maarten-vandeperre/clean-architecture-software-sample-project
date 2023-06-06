package com.redhat.demo.infra.dataproviders.inmemory.repositories

import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class InMemoryPersonRepository : PersonRepository {
    override fun save(person: PersonRepository.DbPerson): String {
        db[person.ref] = person
        return person.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        return db.contains(ref)
    }

    override fun delete(ref: UUID) {
        db.remove(ref)
    }

    override fun get(ref: UUID): PersonRepository.DbPerson {
        return db.get(ref)!!
    }

    override fun search(): List<PersonRepository.DbPerson> {
        return db.values.toList()
    }

    companion object {
        private val db: MutableMap<UUID, PersonRepository.DbPerson> = ConcurrentHashMap<UUID, PersonRepository.DbPerson>()
    }
}