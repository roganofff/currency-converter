package com.intern.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ConversionResult(
    val result: String,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String,
    @SerializedName("time_next_update_utc")
    val timeNextUpdateUtc: String,
    @SerializedName("base_code")
    val baseCode: String,
    @SerializedName("conversion_rates")
    val conversionRates: HashMap<String, Double>
): Serializable
