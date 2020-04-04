package com.taxqwe.convertercore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [AvailableCurrenciesEntity::class], version = 1)
@TypeConverters(BigDecimalTypeConverter::class)
abstract class CurrencyDB: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}