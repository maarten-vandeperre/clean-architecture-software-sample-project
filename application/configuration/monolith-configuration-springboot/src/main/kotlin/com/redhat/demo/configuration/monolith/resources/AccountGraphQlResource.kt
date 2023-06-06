package com.redhat.demo.configuration.monolith.resources

import com.redhat.demo.core.usecases.v1.account.SearchAccountsUseCase
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class AccountResource(
    private val searchAccountsUseCase: SearchAccountsUseCase
) {

    @QueryMapping("allAccounts")
    fun allAccounts(): List<AccountData> {
        return searchAccountsUseCase.execute(SearchAccountsUseCase.Request()).accounts.map {
            AccountData(
                personRef = it.personRef,
                firstName = it.firstName,
                lastName = it.lastName,
                birthDate = it.birthDate,
                addressRef = it.addressRef,
                addressLine1 = it.addressLine1,
                addressLine2 = it.addressLine2,
                addressLine3 = it.addressLine3,
                countryIsoCode = it.countryIsoCode
            )
        }
    }
}

@SchemaMapping("Account")
data class AccountData(
    val personRef: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String?,
    val addressRef: String,
    val addressLine1: String,
    val addressLine2: String,
    val addressLine3: String?,
    val countryIsoCode: String
)