package com.taxqwe.convertercore

import android.annotation.SuppressLint
import android.util.Log
import com.taxqwe.convertercore.db.AvailableCurrenciesEntity
import com.taxqwe.convertercore.db.CurrencyDB
import com.taxqwe.convertercore.dto.LatestRate
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.math.MathContext

class CurrencyRepository(
    private val api: CurrencyApi,
    private val db: CurrencyDB
): CurrencyModel {

    @SuppressLint("CheckResult")
    fun loadAvailableCurrenciesAndSave() {
        api.updateCurrencyList("USD").subscribeOn(Schedulers.io())
            .subscribe({ latestRate: LatestRate ->
                val currencyCodes = mutableListOf<AvailableCurrenciesEntity>()
                latestRate.rates.forEach { (currency, value) ->
                    currencyCodes.add(
                        AvailableCurrenciesEntity(
                            currency.numericCode,
                            currency.currencyCode,
                            value.toBigDecimalOrNull() ?: BigDecimal.ZERO
                        )
                    )
                }
                db.currencyDao().insertCurrencies(currencyCodes)
                db.currencyDao().getCurrencies().subscribe { it.forEach { Log.d("currency", it) } }
            }, {
                it.printStackTrace()
            })
    }

    override fun convert(currencyFrom: String, currencyTo: String, amount: String): Observable<ConverterResultData> {
        if (amount == "0") {
            return Observable.just(ConverterResultData("0", true))
        }

        return Observable.combineLatest(
            db.currencyDao()
                .getUsdPerConventionalUnit(currencyFrom)
                .map { BigDecimal(it) }
                .map { BigDecimal(amount).divide(it, MathContext.DECIMAL32) }
            ,
            db.currencyDao()
                .getUsdPerConventionalUnit(currencyTo)
                .map { BigDecimal(it) },
            BiFunction { t1, t2 -> ConverterResultData(t1.multiply(t2).round(MathContext.DECIMAL32).toString(), false) })
    }

}

interface CurrencyModel {
    fun convert(currencyFrom: String, currencyTo: String, amount: String): Observable<ConverterResultData>
}

data class ConverterResultData(val result: String, val isActual: Boolean)