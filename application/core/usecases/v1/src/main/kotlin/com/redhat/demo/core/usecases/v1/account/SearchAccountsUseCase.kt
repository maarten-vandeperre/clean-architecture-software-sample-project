package com.redhat.demo.core.usecases.v1.account

import com.redhat.demo.core.domain.v1.ReadAccount
import com.redhat.demo.core.usecases.repositories.v1.AccountRepository
import java.util.*

interface SearchAccountsUseCase {

    @Throws(ValidationException::class)
    fun execute(requestData: Request): Response

    class Request

    data class Response(
        val accounts: List<ReadAccount>
    )

    class ValidationException(message: String) : Exception(message)
}

class DefaultSearchAccountsUseCase(
    private val accountRepository: AccountRepository
) : SearchAccountsUseCase {
    override fun execute(requestData: SearchAccountsUseCase.Request): SearchAccountsUseCase.Response {
        return SearchAccountsUseCase.Response(
            accountRepository.search().map {
                ReadAccount(
                    personRef = it.personRef.toString(),
                    firstName = it.firstName,
                    lastName = it.lastName,
                    birthDate = it.birthDate,
                    addressRef = it.addressRef.toString(),
                    addressLine1 = it.addressLine1,
                    addressLine2 = it.addressLine2,
                    addressLine3 = it.addressLine3,
                    countryIsoCode = it.countryIsoCode
                )
            }
        )
    }

}