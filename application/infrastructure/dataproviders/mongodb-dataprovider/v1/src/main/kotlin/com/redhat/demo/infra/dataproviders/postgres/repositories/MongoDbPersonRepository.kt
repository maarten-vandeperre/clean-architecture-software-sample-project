package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.set
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import org.bson.Document
import java.util.*


class MongoDbPersonRepository(
    private val collection: MongoCollection<Document>
) : PersonRepository {

    override fun save(person: PersonRepository.DbPerson): String {
        if (exists(person.ref)) {
            collection.updateOne(
                eq("ref", person.ref.toString()),
                Document("\$set",
                    Document()
                        .append("firstName", person.firstName)
                        .append("lastName", person.lastName)
                        .append("birthDate", person.birthDate)
                        .append("addressRef", person.addressRef?.let { it.toString() }))
            )
        } else {
            collection.insertOne(
                Document()
                    .append("ref", person.ref.toString())
                    .append("firstName", person.firstName)
                    .append("lastName", person.lastName)
                    .append("birthDate", person.birthDate)
                    .append("addressRef", person.addressRef?.let { it.toString() })
            )
        }
        return person.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        return collection.find(eq("ref", ref.toString())).count() > 0
    }

    override fun delete(ref: UUID) {
        collection.deleteOne(eq("ref", ref.toString()))
    }

    override fun get(ref: UUID): PersonRepository.DbPerson? {
        val data = collection.find(eq("ref", ref.toString())).first()
        return data?.let {
            PersonRepository.DbPerson(
                ref = UUID.fromString(it.getString("ref")),
                firstName = it.getString("firstName"),
                lastName = it.getString("lastName"),
                birthDate = it.getString("birthDate"),
                addressRef = it.getString("addressRef")?.let { UUID.fromString(it) }
            )
        }
    }

    override fun search(): List<PersonRepository.DbPerson> {
        return collection.find().toList().map {
            PersonRepository.DbPerson(
                ref = UUID.fromString(it.getString("ref")),
                firstName = it.getString("firstName"),
                lastName = it.getString("lastName"),
                birthDate = it.getString("birthDate"),
                addressRef = it.getString("addressRef")?.let { UUID.fromString(it) }
            )
        }
    }
}