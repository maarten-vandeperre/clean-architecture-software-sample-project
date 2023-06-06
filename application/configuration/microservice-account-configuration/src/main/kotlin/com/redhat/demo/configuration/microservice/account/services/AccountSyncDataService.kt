package com.redhat.demo.configuration.microservice.account.services

import com.mongodb.client.MongoDatabase
import com.redhat.demo.configuration.microservice.account.config.AddressMicroServiceMongo
import com.redhat.demo.configuration.microservice.account.config.Mongo
import com.redhat.demo.configuration.microservice.account.config.PersonMicroServiceMongo
import com.redhat.demo.configuration.microservice.account.config.Postgres
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbAddressRepository
import com.redhat.demo.infra.dataproviders.postgres.repositories.MongoDbPersonRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class AccountSyncDataService { //TODO this service should be replaced, it's a short term hack/time-saver to not having to implement sync logic

    @Inject
    @Postgres
    private lateinit var personPostgresRepository: PersonRepository

    @Inject
    @Mongo
    private lateinit var personMongoRepository: PersonRepository

    @Inject
    @Postgres
    private lateinit var addressPostgresRepository: AddressRepository

    @Inject
    @Mongo
    private lateinit var addressMongoRepository: AddressRepository

    @Inject
    @PersonMicroServiceMongo
    private lateinit var personMicroServiceMongoRepository: MongoDatabase

    @Inject
    @AddressMicroServiceMongo
    private lateinit var addressMicroServiceMongoRepository: MongoDatabase

    @Inject
    private lateinit var mongoDatabase: MongoDatabase

    fun sync() {
        println("start sync")
        mongoDatabase.getCollection("addresses").drop()
        mongoDatabase.getCollection("people").drop()
        personPostgresRepository.search().forEach { personMongoRepository.save(it) } //TODO to use case
        addressPostgresRepository.search().forEach { addressMongoRepository.save(it) } //TODO to use case
        MongoDbPersonRepository(personMicroServiceMongoRepository.getCollection("people")).search().forEach { personMongoRepository.save(it) } //TODO to use case
        MongoDbAddressRepository(addressMicroServiceMongoRepository.getCollection("addresses")).search().forEach { addressMongoRepository.save(it) } //TODO to use case
        println("end sync")
    }
}