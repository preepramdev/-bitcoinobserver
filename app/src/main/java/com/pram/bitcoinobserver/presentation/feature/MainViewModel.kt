package com.pram.bitcoinobserver.presentation.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import com.pram.bitcoinobserver.domain.usecase.main.GetCurrentCoinPriceUseCase
import com.pram.bitcoinobserver.domain.usecase.main.SaveCoinPriceToHistoryUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCurrentCoinPriceUseCase: GetCurrentCoinPriceUseCase,
    private val saveCoinPriceToHistoryUseCase: SaveCoinPriceToHistoryUseCase
) : ViewModel() {

    private var isLiveNeeded = true
    private var lastShownCoinPrice: CoinPriceModel? = null

    private val _coinPrice = MutableLiveData<CoinPriceModel>()
    val coinPrice: LiveData<CoinPriceModel> = _coinPrice

    private val _isLive = MutableLiveData<Boolean>()
    val isLive: LiveData<Boolean> = _isLive

    fun setIsLiveNeeded(isNeedLive: Boolean) {
        this.isLiveNeeded = isNeedLive
        _isLive.value = this.isLiveNeeded
    }

    fun refreshCoinPrice() {
        if (isLiveNeeded) {
            getCurrentCoinPrice()
        } else {
            showLastShownCoinPrice()
        }
    }

    private fun getCurrentCoinPrice() = viewModelScope.launch {
        getCurrentCoinPriceUseCase.execute()
            .catch { exception ->
                exception.printStackTrace()
            }
            .flatMapConcat { coinPriceModel ->
                saveCoinPriceToHistoryUseCase.execute(coinPriceModel).map { coinPriceModel }
            }.collect { coinPriceModel ->
                setIsLiveNeeded(true)
                showCoinPrice(coinPriceModel)
            }
    }

    fun setCoinPriceFromHistory(coinPriceModel: CoinPriceModel) {
        setIsLiveNeeded(false)
        showCoinPrice(coinPriceModel)
    }

    private fun showCoinPrice(coinPriceModel: CoinPriceModel) {
        lastShownCoinPrice = coinPriceModel
        _coinPrice.value = coinPriceModel
    }

    private fun showLastShownCoinPrice() {
        lastShownCoinPrice?.let { _lastShownCoinPrice ->
            _coinPrice.value = _lastShownCoinPrice
        }
    }
}