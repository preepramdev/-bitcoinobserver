package com.pram.bitcoinobserver.presentation.feature.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pram.bitcoinobserver.databinding.ItemCoinPriceHistoryBinding

class CoinPriceHistoryItemAdapter(
    private val historyList: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<CoinPriceHistoryItemAdapter.CoinPriceHistoryItemViewHolder>() {

    fun setHistoryList(historyList: List<String>) {
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

        fun setup(history: String) {
            binding.tvFetchTime.text = history
        }
    }
}