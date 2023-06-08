package com.redhat.demo.configuration.monolith.config

import com.redhat.demo.configuration.monolith.repositories.WithOutboxAddressRepository
import com.redhat.demo.configuration.monolith.repositories.WithOutboxPersonRepository
import com.redhat.demo.core.usecases.repositories.v1.AccountRepository
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.core.repositories.JdbcTemplate
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryAccountRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryAddressRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryOutboxRepository
import com.redhat.demo.infra.dataproviders.inmemory.repositories.InMemoryPersonRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresAccountRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresAddressRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresJdbcTemplate
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresOutboxRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.PostgresPersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class RepositoryConfig(
    @ConfigProperty(name = "db.type") dbType: String,
    @ConfigProperty(name = "db.connection_string", defaultValue = "not-set") connectionUrl: String,
    @ConfigProperty(name = "db.user", defaultValue = "not-set") user: String,
    @ConfigProperty(name = "db.password", defaultValue = "not-set") password: String?
) {
    private val postgresJdbcTemplate: JdbcTemplate?
    private val databaseType: DatabaseType

    init {
        this.databaseType = when (dbType) {
            "IN_MEMORY" -> DatabaseType.IN_MEMORY
            "POSTGRES" -> DatabaseType.POSTGRES
            else -> throw IllegalStateException("$dbType is not yet supported")
        }
        if (databaseType == DatabaseType.POSTGRES) {
            this.postgresJdbcTemplate = PostgresJdbcTemplate(connectionUrl!!, user!!, password!!)
        } else {
            this.postgresJdbcTemplate = null
        }
    }

    @Produces
    fun personRepository(): PersonRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> WithOutboxPersonRepository(InMemoryPersonRepository(), InMemoryOutboxRepository())
            DatabaseType.POSTGRES -> WithOutboxPersonRepository(
                PostgresPersonRepository(postgresJdbcTemplate!!),
                PostgresOutboxRepository(postgresJdbcTemplate, "people_changed")
            )
        }
    }

    @Produces
    fun addressRepository(): AddressRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> WithOutboxAddressRepository(InMemoryAddressRepository(), InMemoryOutboxRepository())
            DatabaseType.POSTGRES -> WithOutboxAddressRepository(
                PostgresAddressRepository(postgresJdbcTemplate!!),
                PostgresOutboxRepository(postgresJdbcTemplate, "addresses_changed")
            )
        }
    }

    @Produces
    fun accountRepository(addressRepository: AddressRepository, personRepository: PersonRepository): AccountRepository {
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryAccountRepository(addressRepository, personRepository)
            DatabaseType.POSTGRES -> PostgresAccountRepository(postgresJdbcTemplate!!)
        }
    }

    enum class DatabaseType {
        IN_MEMORY, POSTGRES
    }
}