package com.taxqwe.convertercore

import com.taxqwe.convertercore.dto.LatestRate
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/latest")
    fun updateCurrencyList(@Query("base") baseCurrencyCode: String) : Observable<LatestRate>

}