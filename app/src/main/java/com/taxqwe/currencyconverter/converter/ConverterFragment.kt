package com.taxqwe.currencyconverter.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.taxqwe.currencyconverter.ConverterApp
import com.taxqwe.currencyconverter.R
import com.taxqwe.currencyconverter.currencySelection.CurrencySelectionDialog
import com.taxqwe.currencyconverter.di.singletones.SelectedCurrenciesRepo
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_converter.*
import kotlinx.android.synthetic.main.fragment_converter.view.*
import kotlinx.android.synthetic.main.layout_currency_row.view.*
import kotlinx.android.synthetic.main.layout_keyboard.view.*
import javax.inject.Inject

class ConverterFragment : Fragment(R.layout.fragment_converter), ConverterView {

    @Inject
    lateinit var presenter: ConverterPresenter

    @Inject
    lateinit var selectedCurrenciesRepo: SelectedCurrenciesRepo

    private val composite = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ConverterApp.instance.applicationComponent.inject(this)
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view?.let { initButtons(it) }
        return view
    }

    private fun initButtons(view: View) {

        //region {keyboard init}
        view.btn0.setOnClickListener {
            presenter.sendAction(UserAction.BtnClick(USER_ACTION_CODE_BTN_0))
        }
        view.btn1.setOnClickListener {
            presenter
                .sendAction(UserAction.BtnClick(USER_ACTION_CODE_BTN_1))
        }
        view.btn2.setOnClickListener {
            presenter.sendAction(
                UserAction.BtnClick(
                    USER_ACTION_CODE_BTN_2
                )
            )
        }
        view.btn3.setOnClickListener {
            presenter.sendAction(
                UserAction.BtnClick(
                    USER_ACTION_CODE_BTN_3
                )
            )
        }
        view.btn4.setOnClickListener {
            presenter.sendAction(
                UserAction.BtnClick(
                    USER_ACTION_CODE_BTN_4
                )
            )
        }
        view.btn5.setOnClickListener {
            presenter.sendAction(
                UserAction.BtnClick(
                    USER_ACTION_CODE_BTN_5
                )
            )
        }
        view.btn6.setOnClickListener {
            presenter.sendAction(
                UserAction.BtnClick(
                    USER_ACTION_CODE_BTN_6
                )
            )
        }
        view.btn7.setOnClickListener {
            presenter.sendAction(
                UserAction.BtnClick(
                    USER_ACTION_CODE_BTN_7
                )
            )
        }
        view.btn8.setOnClickListener {
            presenter.sendAction(
                UserAction.BtnClick(
                    USER_ACTION_CODE_BTN_8
                )
            )
        }
        view.btn9.setOnClickListener {
            presenter.sendAction(
                UserAction.BtnClick(
                    USER_ACTION_CODE_BTN_9
                )
            )
        }
        //endregion

        view.btnAc.setOnClickListener {
            presenter.sendAction(
                UserAction.BtnClick(
                    USER_ACTION_CODE_BTN_AC
                )
            )
        }

        view.btnBack.setOnClickListener {
            presenter.sendAction(
                UserAction
                    .BtnClick(USER_ACTION_CODE_BTN_BACK)
            )
        }

        view.btnDot.setOnClickListener {
            Toast.makeText(
                requireActivity(),
                """Not implemented yet  ¯\_(ツ)_/¯""",
                Toast.LENGTH_SHORT
            ).show()
        }

        view.currencyRow_1.clickable_area.setOnClickListener {
            CurrencySelectionDialog().show(
                requireFragmentManager(),
                CurrencySelectionDialog.SELECT_FIRST_CURRENCY
            )
        }
        view.currencyRow_2.clickable_area.setOnClickListener {
            CurrencySelectionDialog().show(
                requireFragmentManager(),
                CurrencySelectionDialog.SELECT_SECOND_CURRENCY
            )
        }
        view.currencyRow_1.value.setOnClickListener {
            presenter.sendAction(
                UserAction.FocusChanged(
                    USER_ACTION_CODE_FOCUS_FIRST_ROW
                )
            )
        }
        view.currencyRow_2.value.setOnClickListener {
            presenter.sendAction(
                UserAction.FocusChanged(
                    USER_ACTION_CODE_FOCUS_SECOND_ROW
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composite.addAll(selectedCurrenciesRepo.getFirstCurrencyObservable()
            .subscribe {
                presenter.sendAction(UserAction.CurrencyChanged(14, it))
                presenter.sendAction(UserAction.BtnClick(USER_ACTION_CODE_BTN_AC))
                currencyRow_1.currency.text = it
            },
            selectedCurrenciesRepo.getSecondCurrencyObservable()
                .subscribe {
                    presenter.sendAction(UserAction.CurrencyChanged(15, it))
                    presenter.sendAction(UserAction.BtnClick(USER_ACTION_CODE_BTN_AC))
                    currencyRow_2.currency.text = it
                })
        presenter.onViewCreated(this)
    }

    override fun applyViewState(viewState: ViewState) {
        currencyRow_1.currency.text = viewState.row1.first
        currencyRow_1.value.text = viewState.row1.second
        currencyRow_2.currency.text = viewState.row2.first
        currencyRow_2.value.text = viewState.row2.second
        if (!viewState.actual) textView.visibility = View.VISIBLE else textView.visibility =
            View.INVISIBLE
        resolveRowSelection(viewState.selectedFirst)
    }

    private fun resolveRowSelection(isFirstSelected: Boolean) {
        val textColor1 = if (isFirstSelected) requireActivity().resources.getColor(
            R.color.colorAccentOrange,
            null
        ) else requireActivity().resources.getColor(R.color.backInBlack, null)
        val textColor2 = if (!isFirstSelected) requireActivity().resources.getColor(
            R.color.colorAccentOrange,
            null
        ) else requireActivity().resources.getColor(R.color.backInBlack, null)

        currencyRow_1.value.setTextColor(textColor1)
        currencyRow_2.value.setTextColor(textColor2)
    }

    override fun onDestroy() {
        super.onDestroy()
        composite.dispose()
    }
}

interface ConverterView {
    fun applyViewState(viewState: ViewState)
}