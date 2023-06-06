package com.redhat.demo.infra.dataproviders.postgres.repositories

import com.redhat.demo.core.usecases.repositories.v1.AccountRepository
import com.redhat.demo.infra.dataproviders.core.repositories.JdbcTemplate
import java.util.*

class PostgresAccountRepository(
    private val jdbcTemplate: JdbcTemplate
) : AccountRepository {
  override fun search(): List<AccountRepository.DbAccount> {
    return jdbcTemplate.queryForList(
        """
                select p.*, a.*, p.ref as p_ref, a.ref as a_ref
                from people p
                join addresses a on a.id = p.address
            """.trimIndent()
    ) {
      AccountRepository.DbAccount(
          personRef = UUID.fromString(it.getString("p_ref")),
          firstName = it.getString("first_name"),
          lastName = it.getString("last_name"),
          birthDate = it.getString("birth_date"),
          addressRef = it.getString("a_ref").let { UUID.fromString(it) },
          addressLine1 = it.getString("address_line1"),
          addressLine2 = it.getString("address_line2"),
          addressLine3 = it.getString("address_line3"),
          countryIsoCode = it.getString("country_code")
      )
    }
  }
}