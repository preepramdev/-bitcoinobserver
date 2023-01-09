package com.pram.bitcoinobserver.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pram.bitcoinobserver.R
import com.pram.bitcoinobserver.databinding.FragmentHomeBinding
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    companion object {
//        private const val DELAY_UPDATE_TIME = 1000L * 60
        private const val DELAY_UPDATE_TIME = 2000L
    }

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<HomeViewModel>()

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
        viewModel.refreshCoinPrice()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {
            while (isActive) {
                delay(DELAY_UPDATE_TIME)
                viewModel.refreshCoinPrice()
            }
        }
    }


    private fun initView() = with(binding) {
        swLive.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setIsLiveNeeded(isChecked)
        }

        btnHistory.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_historyDialogFragment)
        }
    }

    private fun observeViewModel() = with(viewModel) {
        isLive.observe(viewLifecycleOwner) { _isLive ->
            binding.swLive.isChecked = _isLive
        }

        coinPrice.observe(viewLifecycleOwner) { _coinPrice ->
            showCoinPrice(_coinPrice)
        }
    }

    private fun showCoinPrice(coinPrice: CoinPriceModel) = with(binding) {
        val usd = coinPrice.bpi?.usd
        val gbp = coinPrice.bpi?.gbp
        val eur = coinPrice.bpi?.eur

        tvLastUpdate.text = coinPrice.fetchTime

        currencyWidgetUsd.apply {
            setCurrencyCode(usd?.code.orEmpty())
            setSymbol(usd?.symbol.orEmpty())
            setDescription(usd?.description.orEmpty())
            setRate(usd?.rate.orEmpty())
        }

        currencyWidgetGbp.apply {
            setCurrencyCode(gbp?.code.orEmpty())
            setSymbol(gbp?.symbol.orEmpty())
            setDescription(gbp?.description.orEmpty())
            setRate(gbp?.rate.orEmpty())
        }

        currencyWidgetEur.apply {
            setCurrencyCode(eur?.code.orEmpty())
            setSymbol(eur?.symbol.orEmpty())
            setDescription(eur?.description.orEmpty())
            setRate(eur?.rate.orEmpty())
        }
    }
}