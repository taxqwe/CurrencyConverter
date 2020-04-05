package com.taxqwe.currencyconverter.di.singletones

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SelectedCurrenciesRepo {

    private val currency1Subject = BehaviorSubject.create<String>()

    private val currency2Subject = BehaviorSubject.create<String>()

    fun getFirstCurrencyObservable(): Observable<String> = currency1Subject

    fun getSecondCurrencyObservable(): Observable<String> = currency2Subject

    fun setCurrency1Value(value: String) {
        currency1Subject.onNext(value)
    }

    fun setCurrency2Value(value: String) {
        currency2Subject.onNext(value)
    }

}