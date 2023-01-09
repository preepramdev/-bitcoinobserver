package com.pram.bitcoinobserver.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pram.bitcoinobserver.databinding.FragmentHomeBinding
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import com.pram.bitcoinobserver.presentation.feature.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<HomeViewModel>()
    private val mainViewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        observeMainViewModel()
    }

    private fun observeViewModel() = with(viewModel) {
        showCurrencies.observe(viewLifecycleOwner) { currenciesBpi ->
            showCoinPrice(currenciesBpi)
        }
    }

    private fun observeMainViewModel() = with(mainViewModel) {
        coinPrice.observe(viewLifecycleOwner) { _coinPrice ->
            viewModel.setCoinPrice(_coinPrice)
        }
    }

    private fun showCoinPrice(currenciesBpi: CoinPriceModel.Bpi) = with(binding) {
        val usd = currenciesBpi.usd
        val gbp =currenciesBpi.gbp
        val eur = currenciesBpi.eur

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