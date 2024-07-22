package com.intern.domain.models

import java.io.Serializable

data class ExchangeRate (
    val rate: Double,
    val timeLastUpdateUtc: String,
    val baseCurrency: String,
    val desiredCurrency: String
): Serializable
