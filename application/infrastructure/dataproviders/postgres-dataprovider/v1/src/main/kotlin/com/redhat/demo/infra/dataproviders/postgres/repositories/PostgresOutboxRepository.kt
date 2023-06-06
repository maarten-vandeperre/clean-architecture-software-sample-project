package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.redhat.demo.infra.dataproviders.core.domain.OutboxEvent
import com.redhat.demo.infra.dataproviders.core.repositories.JdbcTemplate
import com.redhat.demo.infra.dataproviders.core.repositories.OutboxRepository
import java.time.format.DateTimeFormatter

class PostgresOutboxRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val outboxTableName: String
) : OutboxRepository {
    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
    override fun save(event: OutboxEvent) {
        jdbcTemplate.execute(
            """
                INSERT INTO $outboxTableName (data_ref, action, time_stamp)
                VALUES (?, ?, ?);
            """.trimIndent(),
            listOf(
                event.dataRef.toString(),
                event.action.toString(),
                formatter.format(event.timestamp)
            )
        )
    }
}