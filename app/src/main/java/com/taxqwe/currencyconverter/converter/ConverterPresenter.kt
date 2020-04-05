package com.taxqwe.currencyconverter.converter

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val MAX_SYMBOLS_LESS_THAN = 16

class ConverterPresenter(private val model: ConverterModel) {

    var lastViewState: ViewState? = null

    lateinit var converterView: ConverterView


    fun sendAction(action: UserAction) {
        when (action) {
            is UserAction.FocusChanged -> onFocusChanged(action.code)
            is UserAction.BtnClick -> onButtonClicked(action.code)
            is UserAction.CurrencyChanged -> onCurrencyChanged(action.code, action.currency)
        }
    }

    private fun onCurrencyChanged(code: Int, currency: String) {
        if (code == USER_ACTION_CODE_CURRENCY_CHANGED_ROW_1)
            lastViewState = lastViewState!!.copy(
            row1 = lastViewState!!.row1.copy(
                first = currency
            )
        )

        if (code == USER_ACTION_CODE_CURRENCY_CHANGED_ROW_2)
            lastViewState = lastViewState!!.copy(
            row2 = lastViewState!!.row2.copy(
                first = currency
            )
        )

    }

    private fun onButtonClicked(code: Int) {
        when (code) {
            in USER_ACTION_CODE_BTN_0..USER_ACTION_CODE_BTN_9 -> updateAmount(code)
            USER_ACTION_CODE_BTN_AC -> clearAmountInCurrentRow()
            USER_ACTION_CODE_BTN_BACK -> updateAmount(code)
        }
    }

    private fun clearAmountInCurrentRow() {
        lastViewState?.let {
            if (it.selectedFirst) {
                convert(it.row1.first, it.row2.first, "0")
            } else {
                convert(it.row2.first, it.row1.first, "0", true)
            }
        }
    }

    private fun updateAmount(code: Int) {
        lastViewState?.let {
            if (it.selectedFirst) {
                var amount = it.row1.second
                if (amount[0] == '0') {
                    amount = amount.removePrefix("0")
                }

                if (code == USER_ACTION_CODE_BTN_BACK) {
                    if (amount.isNotEmpty()) {
                        amount = amount.dropLast(1)
                    }

                    if (amount.isEmpty()) amount = "0"
                }

                if (it.row1.second.length < MAX_SYMBOLS_LESS_THAN || UserAction.BtnClick.codeToSymbol(
                        code
                    ).isEmpty()
                ) { // flow add number
                    convert(
                        it.row1.first,
                        it.row2.first,
                        "$amount${UserAction.BtnClick.codeToSymbol(code)}"
                    )
                }

            } else if (!it.selectedFirst) {
                var amount = it.row2.second
                if (amount[0] == '0') {
                    amount = amount.removePrefix("0")
                }

                if (code == USER_ACTION_CODE_BTN_BACK) {
                    if (amount.isNotEmpty()) {
                        amount = amount.dropLast(1)
                    }

                    if (amount.isEmpty()) amount = "0"
                }

                if (it.row2.second.length < MAX_SYMBOLS_LESS_THAN || UserAction.BtnClick.codeToSymbol(
                        code
                    ).isEmpty()
                ) {
                    convert(
                        it.row2.first,
                        it.row1.first,
                        "$amount${UserAction.BtnClick.codeToSymbol(code)}",
                        true
                    )
                }
            }
        }
    }

    private fun onFocusChanged(code: Int) {
        when (code) {
            USER_ACTION_CODE_FOCUS_FIRST_ROW -> { // если теперь в фокусе 1
                if (lastViewState?.selectedFirst != false) {
                    return
                }

                convert(lastViewState!!.row1.first, lastViewState!!.row2.first, "0")
            }
            USER_ACTION_CODE_FOCUS_SECOND_ROW -> { // если теперь в фокусе 2
                if (!this.lastViewState?.selectedFirst!!) {
                    return
                }

                convert(
                    currencyFrom = lastViewState?.row2!!.first,
                    currencyTo = lastViewState?.row1!!.first,
                    amountFrom = "0",
                    invert = true
                )
            }
        }
    }

    fun onViewCreated(view: ConverterView) {
        converterView = view
        val baseCurrency1 = "USD"
        val baseCurrency2 = "RUB"
        val baseAmount = "0"
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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