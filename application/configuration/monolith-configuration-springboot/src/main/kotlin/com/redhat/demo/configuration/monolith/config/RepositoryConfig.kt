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
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class RepositoryConfig(
    //FIXME to support Spring, we need to mark the configuration class "open"
    @Value("\${db.type}") dbType: String, //FIXME custom syntax in order to access properties and default values
    @Value("\${db.connection_string:not-set}") connectionUrl: String,//FIXME custom syntax in order to access properties and default values
    @Value("\${db.user:not-set}") user: String,//FIXME custom syntax in order to access properties and default values
    @Value("\${db.password:not-set}") password: String?,//FIXME custom syntax in order to access properties and default values
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

    @Bean
    open fun personRepository(): PersonRepository {//FIXME to support Spring, we need to mark the bean method "open"
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> WithOutboxPersonRepository(InMemoryPersonRepository(), InMemoryOutboxRepository())
            DatabaseType.POSTGRES -> WithOutboxPersonRepository(
                PostgresPersonRepository(postgresJdbcTemplate!!),
                PostgresOutboxRepository(postgresJdbcTemplate, "people_changed")
            )
        }
    }

    @Bean
    open fun addressRepository(): AddressRepository {//FIXME to support Spring, we need to mark the bean method "open"
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> WithOutboxAddressRepository(InMemoryAddressRepository(), InMemoryOutboxRepository())
            DatabaseType.POSTGRES -> WithOutboxAddressRepository(
                PostgresAddressRepository(postgresJdbcTemplate!!),
                PostgresOutboxRepository(postgresJdbcTemplate, "addresses_changed")
            )
        }
    }

    @Bean
    open fun accountRepository(addressRepository: AddressRepository, personRepository: PersonRepository): AccountRepository {//FIXME to support Spring, we need to mark the bean method "open"
        return when (databaseType) {
            DatabaseType.IN_MEMORY -> InMemoryAccountRepository(addressRepository, personRepository)
            DatabaseType.POSTGRES -> PostgresAccountRepository(postgresJdbcTemplate!!)
        }
    }

    enum class DatabaseType {
        IN_MEMORY, POSTGRES
    }
}