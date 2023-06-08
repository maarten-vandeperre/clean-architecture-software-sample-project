package com.redhat.demo.configuration.microservice.account.config

import com.redhat.demo.core.usecases.repositories.v1.AccountRepository
import com.redhat.demo.core.usecases.v1.account.DefaultSearchAccountsUseCase
import com.redhat.demo.core.usecases.v1.account.SearchAccountsUseCase
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces

@ApplicationScoped
class UseCaseConfig {

    @Produces
    fun searchAccountsUseCase(accountRepository: AccountRepository): SearchAccountsUseCase {
        return DefaultSearchAccountsUseCase(accountRepository)
    }
}