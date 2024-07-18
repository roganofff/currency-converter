package com.intern.data.repository

import com.intern.data.api.RetrofitInstance.apiInstance
import com.intern.domain.models.ConversionResult
import com.intern.domain.repository.RepositoryInterface

class Repository : RepositoryInterface {
    override suspend fun getRates(currency: String): ConversionResult {
        return apiInstance.getExchangeRates(currency = currency)
    }
}