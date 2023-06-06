package com.redhat.demo.configuration.monolith.resources

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

@RestController("/accounts")
class AccountRestResource {

    private val client = java.net.http.HttpClient.newBuilder()
        .build()
    private val mapper = ObjectMapper()

    @GetMapping("/sync-in-memory")
    fun triggerSyncInMemoryChannel() {
        println("trigger sync in memory")
        val data = mapOf(
            "ref" to UUID.randomUUID().toString()
        )

        val request = HttpRequest.newBuilder()
            .uri(URI("http://abc-kn-channel.maarten-playground.svc.cluster.local"))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
            .header("Ce-Id", UUID.randomUUID().toString())
            .header("Ce-Specversion", "1.0")
            .header("Ce-Type", "person-changed")
            .header("Ce-Source", "monolith")
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println("response: " + response.statusCode())
        println("response: " + response.body())
    }

    @GetMapping("/sync-kafka-channel")
    fun triggerSyncKafkaChannel() {
        println("trigger sync kafka channel")
        val data = mapOf(
            "ref" to UUID.randomUUID().toString()
        )

        val request = HttpRequest.newBuilder()
            .uri(URI("http://kafka-address-data-changed-channel-kn-channel.knative-demo-maarten.svc.cluster.local"))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
            .header("Ce-Id", UUID.randomUUID().toString())
            .header("Ce-Specversion", "1.0")
            .header("Ce-Type", "person-changed")
            .header("Ce-Source", "monolith")
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println("response: " + response.statusCode())
        println("response: " + response.body())
    }

    @GetMapping("/sync-kafka-channel2")
    fun triggerSyncKafkaChannel2() {
        println("trigger sync kafka channel 2")
        val data = mapOf(
            "ref" to UUID.randomUUID().toString()
        )

        val request = HttpRequest.newBuilder()
            .uri(URI("http://kafka-channel-ingress.knative-eventing.svc.cluster.local/knative-demo-maarten/kafka-address-data-changed-channel"))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
            .header("Ce-Id", UUID.randomUUID().toString())
            .header("Ce-Specversion", "1.0")
            .header("Ce-Type", "person-changed")
            .header("Ce-Source", "monolith")
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println("response: " + response.statusCode())
        println("response: " + response.body())
    }

    @GetMapping("/sync-kafka-broker")
    fun triggerSyncKafkaBroker() {
        println("trigger sync kafka broker")
        val data = mapOf(
            "ref" to UUID.randomUUID().toString()
        )

        val request = HttpRequest.newBuilder()
            .uri(URI("http://kafka-broker-ingress.knative-eventing.svc.cluster.local/knative-demo-maarten/kafka-native-broker-person-data"))
            .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
            .header("Ce-Id", UUID.randomUUID().toString())
            .header("Ce-Specversion", "1.0")
            .header("Ce-Type", "person-changed")
            .header("Ce-Source", "monolith")
            .header("Content-Type", "application/json")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println("response: " + response.statusCode())
        println("response: " + response.body())
    }
}