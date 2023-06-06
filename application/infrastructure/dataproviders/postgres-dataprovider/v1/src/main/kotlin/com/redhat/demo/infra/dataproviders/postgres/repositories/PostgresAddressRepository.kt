package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.redhat.demo.core.usecases.repositories.v1.AddressRepository
import com.redhat.demo.infra.dataproviders.core.repositories.JdbcTemplate
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class PostgresAddressRepository(
    private val jdbcTemplate: JdbcTemplate
) : AddressRepository {

    override fun save(address: AddressRepository.DbAddress): String {
        if(exists(address.ref)){
            jdbcTemplate.execute(
                """
                update addresses 
                set ref = ?, 
                address_line1 = ?, 
                address_line2 = ?, 
                address_line3 = ?,
                country_code = ?
                where ref = ?;
            """.trimIndent(),
                listOf(
                    address.ref.toString(),
                    address.addressLine1,
                    address.addressLine2,
                    address.addressLine3,
                    address.countryIsoCode,
                    address.ref.toString()
                )
            )
        } else {
            jdbcTemplate.execute(
                """
                INSERT INTO addresses (ref, address_line1, address_line2, address_line3, country_code)
                VALUES (?, ?, ?, ?, ?);
            """.trimIndent(),
                listOf(
                    address.ref.toString(),
                    address.addressLine1,
                    address.addressLine2,
                    address.addressLine3,
                    address.countryIsoCode
                )
            )
        }
        return address.ref.toString()
    }

    override fun exists(ref: UUID): Boolean {
        val result = jdbcTemplate.query(
            "select count(*) as c from addresses where ref = ?",
            listOf(ref.toString())
        ) {
            1 == it.getInt("c")
        }
        return result ?: false
    }

    override fun delete(ref: UUID) {
        jdbcTemplate.execute(
            "delete from addresses where ref = ?",
            listOf(ref.toString())
        )
    }

    override fun get(ref: UUID): AddressRepository.DbAddress? {
        return jdbcTemplate.query(
            "select * from addresses where ref = ?",
            listOf(ref.toString())
        ) {
            AddressRepository.DbAddress(
                ref = UUID.fromString(it.getString("ref")),
                addressLine1 = it.getString("address_line1"),
                addressLine2 = it.getString("address_line2"),
                addressLine3 = it.getString("address_line3"),
                countryIsoCode = it.getString("country_code")
            )
        }
    }

    override fun search(): List<AddressRepository.DbAddress> {
        return jdbcTemplate.queryForList(
            "select * from addresses"
        ) {
            AddressRepository.DbAddress(
                ref = UUID.fromString(it.getString("ref")),
                addressLine1 = it.getString("address_line1"),
                addressLine2 = it.getString("address_line2"),
                addressLine3 = it.getString("address_line3"),
                countryIsoCode = it.getString("country_code")
            )
        }
    }

    companion object {
        private val db: MutableMap<UUID, AddressRepository.DbAddress> = ConcurrentHashMap<UUID, AddressRepository.DbAddress>()
    }
}