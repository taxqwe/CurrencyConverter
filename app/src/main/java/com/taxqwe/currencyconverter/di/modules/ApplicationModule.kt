package com.taxqwe.currencyconverter.di.modules

import android.app.Application
import com.taxqwe.convertercore.db.CurrencyDB
import com.taxqwe.currencyconverter.ConverterApp
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule {

    @Provides
    fun provideCurrencyDB() : CurrencyDB = ConverterApp.instance.dataBase

}