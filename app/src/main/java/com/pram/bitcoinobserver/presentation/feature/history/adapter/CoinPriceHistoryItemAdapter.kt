package com.pram.bitcoinobserver.presentation.feature.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pram.bitcoinobserver.databinding.ItemCoinPriceHistoryBinding
import com.pram.bitcoinobserver.domain.model.CoinPriceModel

class CoinPriceHistoryItemAdapter(
    private val historyList: MutableList<CoinPriceModel> = mutableListOf()
) : RecyclerView.Adapter<CoinPriceHistoryItemAdapter.CoinPriceHistoryItemViewHolder>() {

    fun setHistoryList(historyList: List<CoinPriceModel>) {
        this.historyList.apply {
            clear()
            addAll(historyList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinPriceHistoryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCoinPriceHistoryBinding.inflate(layoutInflater, parent, false)
        return CoinPriceHistoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinPriceHistoryItemViewHolder, position: Int) {
        val history = historyList[position]
        holder.setup(history)
    }

    override fun getItemCount(): Int = historyList.size

    inner class CoinPriceHistoryItemViewHolder(
        private val binding: ItemCoinPriceHistoryBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun setup(history: CoinPriceModel) {
            binding.tvFetchTime.text = history.fetchTime
        }
    }
}