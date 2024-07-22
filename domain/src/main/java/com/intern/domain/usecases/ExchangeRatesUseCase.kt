package com.intern.domain.usecases

import com.intern.domain.models.ConversionResult
import com.intern.domain.repository.RepositoryInterface

class ExchangeRatesUseCase(private val repository: RepositoryInterface) {
    suspend fun getConversion(baseCurrency: String): ConversionResult {
        return repository.getExchangeRates(baseCurrency = baseCurrency)
    }
}