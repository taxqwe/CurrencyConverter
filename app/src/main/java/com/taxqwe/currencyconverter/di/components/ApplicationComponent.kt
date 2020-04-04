package com.taxqwe.currencyconverter.di.components

import com.taxqwe.currencyconverter.converter.recycler.CurrencyListAdapter
import com.taxqwe.currencyconverter.currencySelection.CurrencySelectionDialog
import com.taxqwe.currencyconverter.di.modules.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
@Singleton

interface ApplicationComponent {
    fun inject(currencyListAdapter: CurrencyListAdapter)
    fun inject(currencyListAdapter: CurrencySelectionDialog)
}