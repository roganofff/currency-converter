package com.intern.data.api

import com.intern.domain.models.ConversionResult
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("latest/{currency}")
    suspend fun getExchangeRates(
        @Path("currency") currency: String
    ): ConversionResult
}