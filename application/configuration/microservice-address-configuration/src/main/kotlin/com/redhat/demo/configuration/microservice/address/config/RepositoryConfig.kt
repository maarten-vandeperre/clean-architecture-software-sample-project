package com.redhat.demo.configuration.microservice.address.config

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.redhat.demo.configuration.microservice.address.repositories.WithChannelUpdateAddressRepository
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryAddressRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbAddressRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import org.eclipse.microprofile.config.inject.ConfigProperty


@ApplicationScoped
class RepositoryConfig(
    @ConfigProperty(name = "db.type") dbType: String,
    @ConfigProperty(name = "db.mongo.connection_string", defaultValue = "not-set") mongoConnectionUrl: String,
    @ConfigProperty(name = "channel.address_changed.url", defaultValue = "not-set") val addressChangedChannelUrl: String
) {
    private val mongoDatabase: MongoDatabase?
    private val databaseType: DatabaseType

    init {
        this.databaseType = when (dbType) {
            "IN_MEMORY" -> DatabaseType.IN_MEMORY
            "PHYSICAL" -> DatabaseType.PHYSICAL
            else -> throw IllegalStateException("$dbType is not yet supported")
        }
        if (databaseType == DatabaseType.PHYSICAL) {
            this.mongoDatabase = MongoClients.create(mongoConnectionUrl).getDatabase("microservice-address")
        } else {
            this.mongoDatabase = null
        }
    }

    @Produces
    fun addressRepository(): AddressRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryAddressRepository()
            DatabaseType.PHYSICAL -> WithChannelUpdateAddressRepository(
                MongoDbAddressRepository(mongoDatabase!!.getCollection("addresses")),
                addressChangedChannelUrl
            )
        }
    }

    enum class DatabaseType {
        IN_MEMORY, PHYSICAL
    }
}