package com.intern.data.repository

import com.intern.data.api.RetrofitInstance.apiClient
import com.intern.domain.models.ConversionResult
import com.intern.domain.repository.RepositoryInterface

class Repository : RepositoryInterface {
    override suspend fun getExchangeRates(baseCurrency: String): ConversionResult {
        return apiClient.getExchangeRates(currency = baseCurrency)
    }
}