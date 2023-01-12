package com.pram.bitcoinobserver.presentation.feature.converter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pram.bitcoinobserver.domain.enumModel.CurrencyCodeEnum
import com.pram.bitcoinobserver.domain.model.AmountModel
import com.pram.bitcoinobserver.domain.model.CoinPriceModel

class ConverterViewModel : ViewModel() {

    companion object {
        private const val DEFAULT_INPUT_AMOUNT = 0.0
        private const val DEFAULT_COIN_PRICE_IN_SELECTED_CURRENCY = 0.0
    }

    var amountModel = AmountModel()

    var coinPriceInSelectedCurrency: Double = 0.0

    private val _showCoinAmountResult = MutableLiveData<Double>()
    private val _showCurrencyAmountResult = MutableLiveData<Double>()

    val showCoinAmountResult: LiveData<Double> = _showCoinAmountResult
    val showCurrencyAmountResult: LiveData<Double> = _showCurrencyAmountResult

    fun setCoinPrice(coinPrice: CoinPriceModel) {
        kotlin.runCatching {
            val rate = coinPrice.bpi?.usd?.rate ?: "" // select with currencyCode
            rate.replace(",", "").toDoubleOrNull() ?: DEFAULT_COIN_PRICE_IN_SELECTED_CURRENCY
        }.onSuccess { _coinPriceInSelectedCurrency ->
            coinPriceInSelectedCurrency = _coinPriceInSelectedCurrency
        }.onFailure { exception ->
            exception.printStackTrace()
        }
        Log.e("TAG", "setCoinAmount: $coinPriceInSelectedCurrency")
    }

    fun setCoinAmount(coinInput: String) {
        runCatching {
            coinInput.toDoubleOrNull() ?: DEFAULT_INPUT_AMOUNT
        }.onSuccess { coinAmount ->
            amountModel.coinInputAmount = coinAmount
            calculateCurrencyAmount()
        }.onFailure { exception ->
            exception.printStackTrace()
        }
        Log.e("TAG", "setCoinAmount: $amountModel")
    }

    fun setCurrencyAmount(currencyInput: String, currencyCode: String) {
        runCatching {
            val currencyAmount = currencyInput.toDoubleOrNull() ?: DEFAULT_INPUT_AMOUNT
//            val currencyCodeEnum = CurrencyCodeEnum.valueOf(currencyCode)
            Pair(currencyAmount, CurrencyCodeEnum.USD)
        }.onSuccess { pairCurrency ->
            amountModel.currencyInputAmount = pairCurrency.first
            amountModel.currencyCode = pairCurrency.second
            calculateCoinAmount()
        }.onFailure { exception ->
            exception.printStackTrace()
        }
    }

    private fun calculateCoinAmount() {
        val calculateCoinResult = (amountModel.currencyInputAmount / coinPriceInSelectedCurrency)
        amountModel.coinInputAmount = calculateCoinResult
        _showCoinAmountResult.value = calculateCoinResult
    }

    private fun calculateCurrencyAmount() {
        val calculateCurrencyResult = (amountModel.coinInputAmount * coinPriceInSelectedCurrency)
        amountModel.currencyInputAmount = calculateCurrencyResult
        _showCurrencyAmountResult.value = calculateCurrencyResult
    }
}