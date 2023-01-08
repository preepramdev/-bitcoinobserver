package com.pram.bitcoinobserver.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import com.pram.bitcoinobserver.domain.usecase.GetCurrentCoinPriceUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCurrentCoinPriceUseCase: GetCurrentCoinPriceUseCase
) : ViewModel() {

    private val _coinPrice = MutableLiveData<CoinPriceModel>()
    val coinPrice: LiveData<CoinPriceModel> = _coinPrice

    fun getCurrentCoinPrice() = viewModelScope.launch {
        getCurrentCoinPriceUseCase.execute()
            .catch { exception ->
                exception.printStackTrace()
            }
            .collect { coinPriceModel ->
                _coinPrice.value = coinPriceModel
            }
    }
}