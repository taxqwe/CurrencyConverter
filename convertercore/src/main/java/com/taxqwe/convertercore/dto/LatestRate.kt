package com.taxqwe.convertercore.dto

import android.icu.util.Currency
import android.icu.util.CurrencyAmount
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class LatestRate(
    @JsonProperty("rates") val rates: Map<Currency, String>,
    @JsonProperty("base") val base: Currency,
    @JsonProperty("date") val date: String
)