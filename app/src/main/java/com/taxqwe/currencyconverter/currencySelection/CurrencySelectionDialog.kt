package com.taxqwe.currencyconverter.currencySelection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.taxqwe.convertercore.db.CurrencyDB
import com.taxqwe.currencyconverter.ConverterApp
import com.taxqwe.currencyconverter.R
import com.taxqwe.currencyconverter.converter.recycler.CurrencyListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.currencies_sheet.view.*
import javax.inject.Inject

class CurrencySelectionDialog : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var viewAdapter: CurrencyListAdapter

    private lateinit var viewManager: RecyclerView.LayoutManager

    @Inject
    lateinit var db: CurrencyDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.currencies_sheet, container, false)
        viewManager = LinearLayoutManager(requireActivity())
        viewAdapter = CurrencyListAdapter(mutableListOf())

        recyclerView = v.recycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ConverterApp.instance.applicationComponent.inject(this)
        db.currencyDao().getCurrencies().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
            .mainThread()).subscribe {
            viewAdapter.update(it.toMutableList().apply { sortBy { it } })
        }
    }

}