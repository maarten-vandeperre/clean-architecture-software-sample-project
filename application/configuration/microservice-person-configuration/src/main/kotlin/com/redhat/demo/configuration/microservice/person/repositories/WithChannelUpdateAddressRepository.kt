package com.redhat.demo.configuration.microservice.person.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.redhat.demo.core.usecases.repositories.v1.PersonRepository
import com.redhat.demo.infra.dataproviders.core.domain.OutboxEventAction
import jakarta.transaction.Transactional
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

class WithChannelUpdatePersonRepository(
    private val personRepository: PersonRepository,
    private val personChangedChannelUrl: String
) : PersonRepository {
    private val client = java.net.http.HttpClient.newBuilder()
        .build()
    private val mapper = ObjectMapper()

    private fun broadcastChange(ref: UUID, action: OutboxEventAction) {
        println("trigger broadcast of update channel: " + personChangedChannelUrl)
        val data = mapOf(
            "ref" to UUID.randomUUID().toString(),
            "action" to action.name
        )

        val request = HttpRequest.newBuilder()
            .uri(URI(personChangedChannelUrl))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
            .header("Ce-Id", ref.toString())
            .header("Ce-Specversion", "1.0")
            .header("Ce-Type", "person-changed")
            .header("Ce-Source", "microservice-person")
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println("response: " + response.statusCode())
        println("response: " + response.body())
    }

    @Transactional
    override fun save(person: PersonRepository.DbPerson): String {
        val action = if (personRepository.exists(person.ref)) {
            OutboxEventAction.UPDATED
        } else {
            OutboxEventAction.CREATED
        }
        val result = personRepository.save(person)
        broadcastChange(person.ref, action)
        return result
    }

    override fun exists(ref: UUID): Boolean {
        return personRepository.exists(ref)
    }

    @Transactional
    override fun delete(ref: UUID) {
        personRepository.delete(ref)
        broadcastChange(ref, OutboxEventAction.DELETED)
    }

    override fun get(ref: UUID): PersonRepository.DbPerson? {
        return personRepository.get(ref)
    }

    override fun search(): List<PersonRepository.DbPerson> {
        return personRepository.search()
    }


}