package com.taxqwe.currencyconverter.di.components

import com.taxqwe.currencyconverter.converter.ConverterFragment
import com.taxqwe.currencyconverter.converter.ConverterModel
import com.taxqwe.currencyconverter.currencySelection.CurrencySelectionDialog
import com.taxqwe.currencyconverter.di.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton

interface ApplicationComponent {
    fun inject(currencyListDialog: CurrencySelectionDialog)

    fun inject(currencyListDialog: ConverterFragment)

    fun inject(converterModel: ConverterModel)
}