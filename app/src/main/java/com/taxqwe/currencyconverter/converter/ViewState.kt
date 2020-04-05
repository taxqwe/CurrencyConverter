package com.taxqwe.currencyconverter.converter

data class ViewState(
    val row1: Pair<String, String>,
    val row2: Pair<String, String>,
    val selectedFirst: Boolean,
    val actual: Boolean)

const val USER_ACTION_CODE_BTN_0 = 0
const val USER_ACTION_CODE_BTN_1 = 1
const val USER_ACTION_CODE_BTN_2 = 2
const val USER_ACTION_CODE_BTN_3 = 3
const val USER_ACTION_CODE_BTN_4 = 4
const val USER_ACTION_CODE_BTN_5 = 5
const val USER_ACTION_CODE_BTN_6 = 6
const val USER_ACTION_CODE_BTN_7 = 7
const val USER_ACTION_CODE_BTN_8 = 8
const val USER_ACTION_CODE_BTN_9 = 9
const val USER_ACTION_CODE_BTN_AC = 10
const val USER_ACTION_CODE_BTN_BACK = 11
const val USER_ACTION_CODE_FOCUS_FIRST_ROW = 12
const val USER_ACTION_CODE_FOCUS_SECOND_ROW = 13
const val USER_ACTION_CODE_CURRENCY_CHANGED_ROW_1 = 14
const val USER_ACTION_CODE_CURRENCY_CHANGED_ROW_2 = 15

sealed class UserAction(val code: Int) {

    class BtnClick(code: Int): UserAction(code)

    class FocusChanged(code: Int): UserAction(code)

    class CurrencyChanged(code: Int, currency: String): UserAction(code)

}