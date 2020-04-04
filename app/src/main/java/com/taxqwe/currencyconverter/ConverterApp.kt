package com.taxqwe.currencyconverter

import android.app.Application
import com.taxqwe.convertercore.ConverterCore
import com.taxqwe.convertercore.db.CurrencyDB
import com.taxqwe.currencyconverter.di.components.ApplicationComponent
import com.taxqwe.currencyconverter.di.components.DaggerApplicationComponent

class ConverterApp : Application() {
    lateinit var dataBase: CurrencyDB

    lateinit var applicationComponent: ApplicationComponent

    companion object{
        lateinit var instance: ConverterApp
    }

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()
        val cc = ConverterCore().initialize(this)
        dataBase = cc.db
        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}