package com.taxqwe.convertercore

import android.annotation.SuppressLint
import android.icu.util.Currency
import android.util.Log
import com.taxqwe.convertercore.db.AvailableCurrenciesEntity
import com.taxqwe.convertercore.db.CurrencyDB
import com.taxqwe.convertercore.dto.LatestRate
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.math.MathContext

class CurrencyRepository(
    private val api: CurrencyApi,
    private val db: CurrencyDB,
    private val useCacheEveryRequest: Boolean
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

        return Observable.just(useCacheEveryRequest).switchMap {
            return@switchMap if (it) {
                cacheConvert(currencyFrom, currencyTo, amount)
            } else {
                networkConvert(currencyFrom, currencyTo, amount)
            }
        }
            .onErrorResumeNext(cacheConvert(currencyFrom, currencyTo, amount)) // if network request failed, then try to convert using values from cache

    }

    private fun cacheConvert(currencyFrom: String, currencyTo: String, amount: String): Observable<ConverterResultData> {
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

    private fun networkConvert(
        currencyFrom: String,
        currencyTo: String,
        amount: String
    ): Observable<ConverterResultData> {
        return api.updateCurrencyList(currencyFrom)
            .map {
                ConverterResultData(
                    BigDecimal(amount).multiply(BigDecimal(it.rates[Currency.getInstance(currencyTo)])).round(
                        MathContext.DECIMAL32
                    ).toString(), true
                )
            } // here we knows 1C1 = xC2
    }

}

interface CurrencyModel {
    fun convert(currencyFrom: String, currencyTo: String, amount: String): Observable<ConverterResultData>
}

data class ConverterResultData(val result: String, val isActual: Boolean)