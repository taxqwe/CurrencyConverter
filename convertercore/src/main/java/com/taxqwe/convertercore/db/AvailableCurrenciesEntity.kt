package com.taxqwe.convertercore.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AvailableCurrencies")
data class AvailableCurrenciesEntity(

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,

    var currencyCode: String
)