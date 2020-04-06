package com.taxqwe.convertercore

import android.app.Application
import androidx.room.Room
import com.taxqwe.convertercore.db.CurrencyDB
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

class ConverterCore(private val useCacheEveryRequest: Boolean) {

    lateinit var retrofit: Retrofit

    lateinit var currencyRepository: CurrencyRepository

    lateinit var db: CurrencyDB

    fun initialize(applicationContext: Application): ConverterCore {
        db = Room.databaseBuilder(applicationContext,
            CurrencyDB::class.java, "CurrencyDB").build()


        retrofit = Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

//        val mapper = ObjectMapper().registerModule(KotlinModule())

        currencyRepository = CurrencyRepository(retrofit.create(CurrencyApi::class.java), db, useCacheEveryRequest)
        currencyRepository.loadAvailableCurrenciesAndSave()

        return this
    }
}