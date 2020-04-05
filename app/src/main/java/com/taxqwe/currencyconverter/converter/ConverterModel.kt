package com.taxqwe.currencyconverter.converter

import io.reactivex.Observable

class ConverterModel {

    fun convert(currencyFrom: String, currencyTo: String, amount: String): Observable<ConverterResultData> {
        return Observable.just(ConverterResultData("76.41", true))
    }
}

data class ConverterResultData(val result: String, val isActual: Boolean)