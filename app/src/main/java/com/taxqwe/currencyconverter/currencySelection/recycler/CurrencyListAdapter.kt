package com.taxqwe.currencyconverter.currencySelection.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taxqwe.currencyconverter.R

class CurrencyListAdapter(
    private var dataSet: MutableList<String>,
    private val clickListener: (String) -> Unit
) :
    RecyclerView.Adapter<CurrencyListAdapter.CurrencyListViewHolder>() {

    class CurrencyListViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyListViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false) as TextView
        return CurrencyListViewHolder(textView)
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        holder.textView.text = dataSet[position]
        holder.textView.setOnClickListener { clickListener.invoke(holder.textView.text.toString()) }
    }

    override fun getItemCount() = dataSet.size

    fun update(newData: MutableList<String>) {
        dataSet = newData
        notifyDataSetChanged()
    }

}