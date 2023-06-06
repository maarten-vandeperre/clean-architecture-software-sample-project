package com.redhat.demo.configuration.monolith.resources

import com.redhat.demo.core.usecases.v1.account.SearchAccountsUseCase
import org.eclipse.microprofile.graphql.Description
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Query
import org.eclipse.microprofile.graphql.Type
import java.util.*

@GraphQLApi
class AccountResource(
    private val searchAccountsUseCase: SearchAccountsUseCase
) {

    @Query("allAccounts")
    @Description("Search all accounts in the system")
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

@Type("Account")
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