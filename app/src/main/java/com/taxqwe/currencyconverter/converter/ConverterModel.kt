package com.taxqwe.currencyconverter.converter

import com.taxqwe.convertercore.ConverterResultData
import com.taxqwe.convertercore.CurrencyModel
import com.taxqwe.currencyconverter.ConverterApp
import io.reactivex.Observable
import javax.inject.Inject

class ConverterModel {

    @Inject
    lateinit var currencyModel: CurrencyModel

    init {
        ConverterApp.instance.applicationComponent.inject(this)
    }

    fun convert(currencyFrom: String, currencyTo: String, amount: String): Observable<ConverterResultData> {
        return currencyModel.convert(currencyFrom, currencyTo, amount)
    }
}

