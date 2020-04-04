package com.taxqwe.currencyconverter.converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.taxqwe.currencyconverter.R
import com.taxqwe.currencyconverter.currencySelection.CurrencySelectionDialog
import kotlinx.android.synthetic.main.fragment_converter.*
import kotlinx.android.synthetic.main.fragment_converter.view.*
import kotlinx.android.synthetic.main.layout_currency_row.view.*
import kotlinx.android.synthetic.main.layout_keyboard.view.*

class ConverterFragment : Fragment(R.layout.fragment_converter) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view?.let { initButtons(it) }
        return view
    }

    private fun initButtons(view: View) {
        view.btn0.setOnClickListener { Log.d("btn", "0") }
        view.btn1.setOnClickListener { Log.d("btn", "1") }
        view.btn2.setOnClickListener { Log.d("btn", "2") }
        view.btn3.setOnClickListener { Log.d("btn", "3") }
        view.btn4.setOnClickListener { Log.d("btn", "4") }
        view.btn5.setOnClickListener { Log.d("btn", "5") }
        view.btn6.setOnClickListener { Log.d("btn", "6") }
        view.btn7.setOnClickListener { Log.d("btn", "7") }
        view.btn8.setOnClickListener { Log.d("btn", "8") }
        view.btn9.setOnClickListener { Log.d("btn", "9") }
        view.btnAc.setOnClickListener { Log.d("btn", "AC") }
        view.btnBack.setOnClickListener { Log.d("btn", "back") }
        view.btnDot.setOnClickListener { Log.d("btn", "dot") }

        view.currencyRow_1.clickable_area.setOnClickListener { CurrencySelectionDialog().show(requireFragmentManager(), "benis") }
        view.currencyRow_2.clickable_area.setOnClickListener { CurrencySelectionDialog().show(requireFragmentManager(), "benis") }
    }
}