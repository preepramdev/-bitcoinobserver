package com.pram.bitcoinobserver.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pram.bitcoinobserver.domain.model.CoinPriceModel

class HomeViewModel(
) : ViewModel() {

    private val _showCurrencies = MutableLiveData<CoinPriceModel.Bpi>()
    val showCurrencies: LiveData<CoinPriceModel.Bpi> = _showCurrencies

    fun setCoinPrice(coinPrice: CoinPriceModel) {
        coinPrice.bpi?.let { _coinPrice ->
            _showCurrencies.value = _coinPrice
        }
    }
}