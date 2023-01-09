package com.pram.bitcoinobserver.presentation.feature.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pram.bitcoinobserver.domain.usecase.GetHistoryCoinPricesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getHistoryCoinPricesUseCase: GetHistoryCoinPricesUseCase
) : ViewModel() {

    private val _showHistories = MutableLiveData<List<String>>()
    val showHistories: LiveData<List<String>> = _showHistories

    fun getHistoryCoinPrices() = viewModelScope.launch {
        getHistoryCoinPricesUseCase.execute()
            .catch { exception ->
                exception.printStackTrace()
            }.collect{ histories ->
                _showHistories.value = histories
            }
    }

}