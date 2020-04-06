package com.taxqwe.currencyconverter.di.modules

import com.taxqwe.convertercore.db.CurrencyDB
import com.taxqwe.currencyconverter.ConverterApp
import com.taxqwe.currencyconverter.converter.ConverterModel
import com.taxqwe.currencyconverter.converter.ConverterPresenter
import com.taxqwe.currencyconverter.di.singletones.SelectedCurrenciesRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideCurrencyDB(): CurrencyDB = ConverterApp.instance.dataBase

    @Provides
    @Singleton
    fun provideConverterModel(): ConverterModel = ConverterModel()

    @Provides
    @Singleton
    fun provideConverterPresenter(converterModel: ConverterModel): ConverterPresenter =
        ConverterPresenter(converterModel)

    @Provides
    @Singleton
    fun provideCurrenciesStateRepo(): SelectedCurrenciesRepo = SelectedCurrenciesRepo()

}