package com.pram.bitcoinobserver.presentation.feature.converter.selectcurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pram.bitcoinobserver.domain.enumModel.CurrencyCodeEnum
import com.pram.bitcoinobserver.domain.usecase.converter.GetCurrencyCodesUseCase

class SelectCurrencyViewModel(
    private val getCurrencyCodesUseCase: GetCurrencyCodesUseCase
) : ViewModel() {

    private val _showCurrencyCode = MutableLiveData<List<CurrencyCodeEnum>>()
    val showCurrencyCode: LiveData<List<CurrencyCodeEnum>> = _showCurrencyCode

    fun getCurrencyCodes() {
        _showCurrencyCode.value = getCurrencyCodesUseCase.execute()
    }
}