package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import org.bson.Document
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class MongoDbAddressRepository(
    private val collection: MongoCollection<Document>
) : AddressRepository {

    override fun save(address: AddressRepository.DbAddress): String {
        if (exists(address.ref)) {
            collection.updateOne(
                Filters.eq("ref", address.ref.toString()),
                Document()
                    .append("ref", address.ref.toString())
                    .append("addressLine1", address.addressLine1)
                    .append("addressLine2", address.addressLine2)
                    .append("addressLine3", address.addressLine3)
                    .append("countryIsoCode", address.countryIsoCode)
            )
        } else {
            collection.insertOne(
                Document()
                    .append("ref", address.ref.toString())
                    .append("addressLine1", address.addressLine1)
                    .append("addressLine2", address.addressLine2)
                    .append("addressLine3", address.addressLine3)
                    .append("countryIsoCode", address.countryIsoCode)
            )
        }
        return address.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        return collection.find(Filters.eq("ref", ref.toString())).count() > 0
    }

    override fun delete(ref: UUID) {
        collection.deleteOne(Filters.eq("ref", ref.toString()))
    }

    override fun get(ref: UUID): AddressRepository.DbAddress? {
        val data = collection.find(Filters.eq("ref", ref.toString())).first()
        return data?.let {
            AddressRepository.DbAddress(
                ref = UUID.fromString(it.getString("ref")),
                addressLine1 = it.getString("addressLine1"),
                addressLine2 = it.getString("addressLine2"),
                addressLine3 = it.getString("addressLine3"),
                countryIsoCode = it.getString("countryIsoCode")
            )
        }
    }

    override fun search(): List<AddressRepository.DbAddress> {
        return collection.find().toList().map {
            AddressRepository.DbAddress(
                ref = UUID.fromString(it.getString("ref")),
                addressLine1 = it.getString("addressLine1"),
                addressLine2 = it.getString("addressLine2"),
                addressLine3 = it.getString("addressLine3"),
                countryIsoCode = it.getString("countryIsoCode")
            )
        }
    }

    companion object {
        private val db: MutableMap<UUID, AddressRepository.DbAddress> = ConcurrentHashMap<UUID, AddressRepository.DbAddress>()
    }
}