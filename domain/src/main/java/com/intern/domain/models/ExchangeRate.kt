package com.intern.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExchangeRate (
    val rate: Double,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String,
    @SerializedName("base_code")
    val baseCode: String,
): Serializable
