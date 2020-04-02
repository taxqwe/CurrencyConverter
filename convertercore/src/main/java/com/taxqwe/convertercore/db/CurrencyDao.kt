package com.taxqwe.convertercore.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface CurrencyDao {
    @Query("SELECT currencyCode FROM AvailableCurrencies")
    fun getCurrencies(): Observable<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // TODO temporary
    fun insertCurrencies(list: List<AvailableCurrenciesEntity>)
}