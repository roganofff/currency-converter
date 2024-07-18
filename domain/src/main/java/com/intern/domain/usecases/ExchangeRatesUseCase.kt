package com.intern.domain.usecases

import com.intern.domain.models.ExchangeRate
import com.intern.domain.repository.RepositoryInterface

class ExchangeRatesUseCase(private val repository: RepositoryInterface) {
    suspend fun getExchangeRate(baseCurrency: String, desiredCurrency: String): ExchangeRate {
        val response = repository.getExchangeRates(baseCurrency = baseCurrency)

        val rate = response.conversionRates.getValue(desiredCurrency)
        val baseCode = response.baseCode
        val timeLastUpdate = response.timeLastUpdateUtc

        return ExchangeRate(
            rate = rate,
            baseCode = baseCode,
            timeLastUpdateUtc = timeLastUpdate
        )
    }
}