package com.taxqwe.currencyconverter.converter

import com.taxqwe.convertercore.db.CurrencyDB
import com.taxqwe.currencyconverter.ConverterApp
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import javax.inject.Inject

class ConverterModel {

    @Inject
    lateinit var dataBase: CurrencyDB

    init {
        ConverterApp.instance.applicationComponent.inject(this)
    }

    fun convert(currencyFrom: String, currencyTo: String, amount: String): Observable<ConverterResultData> {

        if (amount == "0") {
            return Observable.just(ConverterResultData("0", true))
        }

        return Observable.combineLatest(
            dataBase.currencyDao()
            .getUsdPerConventionalUnit(currencyFrom)
            .map { BigDecimal(it) }
            .map { BigDecimal(amount).divide(it, MathContext.DECIMAL32) }
            ,
            dataBase.currencyDao()
                .getUsdPerConventionalUnit(currencyTo)
                .map { BigDecimal(it) },
            BiFunction { t1, t2 -> ConverterResultData(t1.multiply(t2).round(MathContext.DECIMAL32).toString(), false) })

//            .map{ConverterResultData("0", true)}
    }
}

data class ConverterResultData(val result: String, val isActual: Boolean)