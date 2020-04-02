package com.taxqwe.convertercore.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AvailableCurrenciesEntity::class], version = 1)
abstract class CurrencyDB: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}