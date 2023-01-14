package com.pram.bitcoinobserver.presentation.feature.converter.selectcurrency.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pram.bitcoinobserver.databinding.ItemCurrencyBinding
import com.pram.bitcoinobserver.domain.enumModel.CurrencyCodeEnum

class CurrencyItemAdapter(
    private val currencyCodeList: MutableList<CurrencyCodeEnum> = mutableListOf()
) : RecyclerView.Adapter<CurrencyItemAdapter.CurrencyItemViewHolder>() {

    var onItemClicked: ((CurrencyCodeEnum) -> Unit?)? = null

    fun setupCurrencyList(currencyCodeList: List<CurrencyCodeEnum>) {
        this.currencyCodeList.apply {
            clear()
            addAll(currencyCodeList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(layoutInflater, parent, false)
        return CurrencyItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        val currencyCodeEnum = currencyCodeList[position]
        holder.setup(currencyCodeEnum)
    }

    override fun getItemCount(): Int = currencyCodeList.size

    inner class CurrencyItemViewHolder(
        private val binding: ItemCurrencyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setup(currencyCodeEnum: CurrencyCodeEnum) = with(binding) {
            tvCurrency.text = currencyCodeEnum.code.uppercase()
            root.setOnClickListener {
                onItemClicked?.invoke(currencyCodeEnum)
            }
        }
    }
}