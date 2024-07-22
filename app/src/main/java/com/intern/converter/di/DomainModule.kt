package com.intern.converter.di

import com.intern.domain.usecases.ExchangeRatesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<ExchangeRatesUseCase> {
        ExchangeRatesUseCase(repository = get())
    }
}