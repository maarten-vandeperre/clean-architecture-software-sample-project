package com.redhat.demo.configuration.microservice.address.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.infra.dataproviders.core.domain.OutboxEventAction
import jakarta.transaction.Transactional
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

class WithChannelUpdateAddressRepository(
    private val addressRepository: AddressRepository,
    private val addressChangedChannelUrl: String
) : AddressRepository {
    private val client = java.net.http.HttpClient.newBuilder()
        .build()
    private val mapper = ObjectMapper()

    private fun broadcastChange(ref: UUID, action: OutboxEventAction) {
        println("trigger broadcast of update channel: " + addressChangedChannelUrl)
        val data = mapOf(
            "ref" to UUID.randomUUID().toString(),
            "action" to action.name
        )

        val request = HttpRequest.newBuilder()
            .uri(URI(addressChangedChannelUrl))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
            .header("Ce-Id", ref.toString())
            .header("Ce-Specversion", "1.0")
            .header("Ce-Type", "address-changed")
            .header("Ce-Source", "microservice-address")
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println("response: " + response.statusCode())
        println("response: " + response.body())
    }

    @Transactional
    override fun save(address: AddressRepository.DbAddress): String {
        val action = if (addressRepository.exists(address.ref)) {
            OutboxEventAction.UPDATED
        } else {
            OutboxEventAction.CREATED
        }
        val result = addressRepository.save(address)
        broadcastChange(address.ref, action)
        return result
    }

    override fun exists(ref: UUID): Boolean {
        return addressRepository.exists(ref)
    }

    @Transactional
    override fun delete(ref: UUID) {
        addressRepository.delete(ref)
        broadcastChange(ref, OutboxEventAction.DELETED)
    }

    override fun get(ref: UUID): AddressRepository.DbAddress? {
        return addressRepository.get(ref)
    }

    override fun search(): List<AddressRepository.DbAddress> {
        return addressRepository.search()
    }


}