package com.intern.domain.models

import java.io.Serializable

data class ExchangeRate (
    val rate: Double,
    val amount: String,
    val timeLastUpdateUtc: String,
    val timeNextUpdateUtc: String,
    val baseCurrency: String,
    val desiredCurrency: String
): Serializable
