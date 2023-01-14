package com.pram.bitcoinobserver.presentation.feature.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import com.pram.bitcoinobserver.domain.usecase.history.GetHistoryCoinPricesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getHistoryCoinPricesUseCase: GetHistoryCoinPricesUseCase
) : ViewModel() {

    private val _showHistories = MutableLiveData<List<CoinPriceModel>>()
    val showHistories: LiveData<List<CoinPriceModel>> = _showHistories

    fun getHistoryCoinPrices() = viewModelScope.launch {
        getHistoryCoinPricesUseCase.execute()
            .catch { exception ->
                exception.printStackTrace()
                _showHistories.value = listOf()
            }.collect{ histories ->
                _showHistories.value = histories
            }
    }

}