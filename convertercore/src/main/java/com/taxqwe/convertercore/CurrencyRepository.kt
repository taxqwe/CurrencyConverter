package com.taxqwe.convertercore

import android.annotation.SuppressLint
import android.util.Log
import com.taxqwe.convertercore.db.AvailableCurrenciesEntity
import com.taxqwe.convertercore.db.CurrencyDB
import com.taxqwe.convertercore.dto.LatestRate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal

class CurrencyRepository(
    private val api: CurrencyApi,
    private val db: CurrencyDB
) {

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

}