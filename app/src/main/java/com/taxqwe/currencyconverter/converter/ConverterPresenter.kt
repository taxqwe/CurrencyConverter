package com.taxqwe.currencyconverter.converter

import android.annotation.SuppressLint

class ConverterPresenter(private val model: ConverterModel) {

    var lastViewState: ViewState? = null

    lateinit var converterView: ConverterView

    fun sendAction(action: UserAction) {
        when (action) {
            is UserAction.FocusChanged -> onFocusChanged(action.code)
        }

    }

    private fun onFocusChanged(code: Int) {
        when (code) {
            USER_ACTION_CODE_FOCUS_FIRST_ROW -> { // если теперь в фокусе 1
                if (lastViewState?.selectedFirst != false) {
                    return
                }

                convert(lastViewState!!.row1.first, lastViewState!!.row2.first, "1")
            }
            USER_ACTION_CODE_FOCUS_SECOND_ROW -> { // если теперь в фокусе 2
                if (!this.lastViewState?.selectedFirst!!) {
                    return
                }

                convert(
                    currencyFrom = lastViewState?.row2!!.first,
                    currencyTo = lastViewState?.row1!!.first,
                    amountFrom = "1",
                    invert = true
                )
            }
        }
    }

    fun onViewCreated(view: ConverterView) {
        converterView = view
        val baseCurrency1 = "USD"
        val baseCurrency2 = "RUB"
        val baseAmount = "1"
        convert(baseCurrency1, baseCurrency2, baseAmount)
    }

    @SuppressLint("CheckResult")
    private fun convert(
        currencyFrom: String,
        currencyTo: String,
        amountFrom: String,
        invert: Boolean = false
    ) {
        model.convert(currencyFrom, currencyTo, amountFrom)
            .subscribe({
                applyViewState(
                    ViewState(
                        if (!invert) currencyFrom to amountFrom else currencyTo to it.result,
                        if (!invert) currencyTo to it.result else currencyFrom to amountFrom,
                        lastViewState?.let { currencyFrom == lastViewState!!.row1.first } ?: true,
                        it.isActual
                    )
                )
            }, { it.printStackTrace() })
    }

    private fun applyViewState(viewState: ViewState) {
        lastViewState = viewState
        converterView.applyViewState(viewState)
    }

}