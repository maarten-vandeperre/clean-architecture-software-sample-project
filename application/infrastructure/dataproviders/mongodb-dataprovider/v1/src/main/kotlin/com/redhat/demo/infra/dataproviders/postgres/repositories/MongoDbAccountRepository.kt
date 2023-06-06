package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.mongodb.client.MongoCollection
import com.redhat.demo.core.usecases.repositories.v1.AccountRepository
import org.bson.Document
import java.util.*

class MongoDbAccountRepository(
    private val peopleCollection: MongoCollection<Document>,
    private val addressCollection: MongoCollection<Document>
) : AccountRepository {
    private val addressRepository = MongoDbAddressRepository(addressCollection)
    private val personRepository = MongoDbPersonRepository(peopleCollection)

    override fun search(): List<AccountRepository.DbAccount> {
        val addresses = addressRepository.search().groupBy { it.ref }.toMap().mapValues { it.value[0] }
        return personRepository.search()
            .filter { it.addressRef != null }
            .filter { addresses.containsKey(it.addressRef!!) }
            .map { person ->
                val address = addresses[person.addressRef]!!
                AccountRepository.DbAccount(
                    personRef = person.ref,
                    firstName = person.firstName,
                    lastName = person.lastName,
                    birthDate = person.birthDate,
                    addressRef = address.ref,
                    addressLine1 = address.addressLine1,
                    addressLine2 = address.addressLine2,
                    addressLine3 = address.addressLine3,
                    countryIsoCode = address.countryIsoCode
                )
            }
        //TODO
//        return collection.find().toList().map {
//            AccountRepository.DbAccount(
//                personRef = UUID.fromString(it.getString("personRef")),
//                firstName = it.getString("firstName"),
//                lastName = it.getString("lastName"),
//                birthDate = it.getString("birthDate"),
//                addressRef = it.getString("addressRef").let { UUID.fromString(it) },
//                addressLine1 = it.getString("addressLine1"),
//                addressLine2 = it.getString("addressLine2"),
//                addressLine3 = it.getString("addressLine3"),
//                countryIsoCode = it.getString("countryIsoCode")
//            )
//        }
    }
}