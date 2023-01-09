package com.pram.bitcoinobserver.presentation.feature.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pram.bitcoinobserver.databinding.DialogHistoryBinding
import com.pram.bitcoinobserver.presentation.feature.history.adapter.CoinPriceHistoryItemAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryDialogFragment : DialogFragment() {

    private val binding by lazy { DialogHistoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<HistoryViewModel>()

    private val coinPriceHistoryItemAdapter = CoinPriceHistoryItemAdapter(
//        mutableListOf("sfsf", "dfsfsfsffd")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeViewModel()
        viewModel.getHistoryCoinPrices()
    }

    private fun initView() = with(binding) {
        rvHistories.apply {
            adapter = coinPriceHistoryItemAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() = with(viewModel) {
        showHistories.observe(viewLifecycleOwner) { histories ->
            coinPriceHistoryItemAdapter.setHistoryList(histories)
        }
    }
}