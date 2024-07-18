package com.intern.domain.repository

import com.intern.domain.models.ConversionResult

interface RepositoryInterface {
    suspend fun getExchangeRates(baseCurrency: String): ConversionResult
}