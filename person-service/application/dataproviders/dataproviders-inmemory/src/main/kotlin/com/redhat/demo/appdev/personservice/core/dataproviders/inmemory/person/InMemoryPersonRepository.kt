package com.redhat.demo.appdev.personservice.core.dataproviders.inmemory.person

import com.redhat.demo.appdev.personservice.core.coreutils.ExecutionResult
import com.redhat.demo.appdev.personservice.core.coreutils.ExecutionResult.Companion.success
import com.redhat.demo.appdev.personservice.core.domain.address.Address
import com.redhat.demo.appdev.personservice.core.domain.address.Country
import com.redhat.demo.appdev.personservice.core.domain.common.Date
import com.redhat.demo.appdev.personservice.core.domain.person.Person
import com.redhat.demo.appdev.personservice.core.domain.person.PersonRef
import com.redhat.demo.appdev.personservice.core.usecases.person.PersonRepository
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class InMemoryPersonRepository : PersonRepository {

  override fun getPeople(): ExecutionResult<List<Person>> {
    return success(db.map { it.data })
  }

  override fun addPerson(person: Person): ExecutionResult<PersonRef> {
    db.add(DbPerson(id = personSequencer.getAndIncrement(), data = person))
    db.sortBy { it.id }
    return success(person.ref)
  }

  companion object {
    private val personSequencer = AtomicLong(1)
    private val db = mutableListOf(
            DbPerson(
                    id = personSequencer.getAndIncrement(),
                    data = Person(
                            ref = UUID.fromString("fa82dfe6-0619-46d3-b8ee-0d5f039e55f6"),
                            firstName = "Maarten",
                            lastName = "Vandeperre",
                            birthDate = Date(day = 17, month = 4, year = 1989),
                            address = Address(
                                    ref = UUID.fromString("2341e1e8-334a-4f6a-8c73-35212f0b04fd"),
                                    addressLine1 = "Hoofdweg Noord 8",
                                    addressLine2 = "4581JE Terneuzen",
                                    country = Country.NL
                            )
                    )
            ),
            DbPerson(
                    id = personSequencer.getAndIncrement(),
                    data = Person(
                            ref = UUID.fromString("fbba5a9c-53c9-42d2-9794-1bf6165c772e"),
                            firstName = "Jolien",
                            lastName = "Vereman",
                            birthDate = Date(day = 2, month = 8, year = 1992),
                            address = Address(
                                    ref = UUID.fromString("bbf383bf-0202-4865-90e4-6d2c0e83fe2b"),
                                    addressLine1 = "Hoofdweg Noord 8",
                                    addressLine2 = "4581JE Terneuzen",
                                    country = Country.NL
                            )
                    )
            ),
            DbPerson(
                    id = personSequencer.getAndIncrement(),
                    data = Person(
                            ref = UUID.fromString("3f332ac6-7204-4b75-a3ed-d2d0558b5f21"),
                            firstName = "Pieter",
                            lastName = "Vandeperre",
                            birthDate = Date(day = 1, month = 9, year = 1991),
                            address = Address(
                                    ref = UUID.fromString("adf0bb6f-155e-4029-b585-d6e94bd4a27b"),
                                    addressLine1 = "Stationstraat 7",
                                    addressLine2 = "9185 Wachtebeke",
                                    country = Country.BE
                            )
                    )
            ),
            DbPerson(
                    id = personSequencer.getAndIncrement(),
                    data = Person(
                            ref = UUID.fromString("486d72a3-526a-4945-9dc6-765fbe83ba4f"),
                            firstName = "John",
                            lastName = "Doe",
                            birthDate = Date(day = 20, month = 11, year = 1987),
                            address = Address(
                                    ref = UUID.fromString("2b173645-7e71-42fc-8fee-f05a0cd4698c"),
                                    addressLine1 = "Leonardo da Vincilaan 19",
                                    addressLine2 = "1831 Zaventem",
                                    country = Country.BE
                            )
                    )
            )
    )
  }

}