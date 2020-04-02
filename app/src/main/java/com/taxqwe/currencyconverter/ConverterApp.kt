package com.taxqwe.currencyconverter

import android.app.Application
import com.taxqwe.convertercore.ConverterCore
import com.taxqwe.convertercore.db.CurrencyDB

class ConverterApp : Application() {

    lateinit var dataBase: CurrencyDB

    override fun onCreate() {
        super.onCreate()
        val cc = ConverterCore().initialize(this)
        dataBase = cc.db
    }
}