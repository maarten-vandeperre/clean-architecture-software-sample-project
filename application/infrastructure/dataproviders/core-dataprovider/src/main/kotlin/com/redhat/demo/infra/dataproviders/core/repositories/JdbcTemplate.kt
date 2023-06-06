package com.redhat.demo.infra.dataproviders.core.repositories

import java.sql.ResultSet

interface JdbcTemplate {
    fun execute(query: String, params: List<Any?> = emptyList())

    @Throws(JdbcException::class)
    fun <T> query(query: String, params: List<Any?> = emptyList(), mapper: (rs: ResultSet) -> T): T?

    @Throws(JdbcException::class)
    fun <T> queryForList(query: String, params: List<Any?> = emptyList(), mapper: (rs: ResultSet) -> T): List<T>

    class JdbcException(message: String) : Exception(message)
}