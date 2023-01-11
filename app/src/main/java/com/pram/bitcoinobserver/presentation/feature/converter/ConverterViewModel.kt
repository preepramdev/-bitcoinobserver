package com.pram.bitcoinobserver.presentation.feature.converter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pram.bitcoinobserver.domain.enumModel.CurrencyCodeEnum
import com.pram.bitcoinobserver.domain.model.AmountModel
import com.pram.bitcoinobserver.domain.model.CoinPriceModel

class ConverterViewModel : ViewModel() {

    var amountModel = AmountModel()

    var coinPriceInSelectedCurrency: Double = 0.0

    private val _showCoinAmountResult = MutableLiveData<Double>()
    private val _showCurrencyAmountResult = MutableLiveData<Double>()

    val showCoinAmountResult: LiveData<Double> = _showCoinAmountResult
    val showCurrencyAmountResult: LiveData<Double> = _showCurrencyAmountResult

    fun setCoinPrice(coinPrice: CoinPriceModel) {
        kotlin.runCatching {
            val rate = coinPrice.bpi?.usd?.rate ?: "" // select with currencyCode
            rate.replace(",", "").toDouble()
        }.onSuccess { _coinPriceInSelectedCurrency ->
            coinPriceInSelectedCurrency = _coinPriceInSelectedCurrency
//            calculateCoinAmount()
//            calculateCurrencyAmount()
        }.onFailure { exception ->
            exception.printStackTrace()
        }
        Log.e("TAG", "setCoinAmount: $coinPriceInSelectedCurrency")
    }

    fun setCoinAmount(coinInput: String) {
        runCatching {
            coinInput.toDouble()
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
            val currencyAmount = currencyInput.toDouble()
//            val currencyCodeEnum = CurrencyCodeEnum.valueOf(currencyCode)
            Pair(currencyAmount, CurrencyCodeEnum.USD)
        }.onSuccess { pairCurrency ->
            amountModel.currencyInputAmount = pairCurrency.first
            amountModel.currencyCode = pairCurrency.second
            calculateCoinAmount()
        }.onFailure { exception ->
            exception.printStackTrace()
        }
        Log.e("TAG", "setCurrencyAmount: $amountModel")
    }

    private fun calculateCoinAmount() {
        val calculateCoinResult = (amountModel.currencyInputAmount / coinPriceInSelectedCurrency)
        Log.e("TAG", "calculateCoinAmount: $calculateCoinResult")
        amountModel.coinInputAmount = calculateCoinResult
        _showCoinAmountResult.value = calculateCoinResult
    }

    private fun calculateCurrencyAmount() {
        val calculateCurrencyResult = (amountModel.coinInputAmount * coinPriceInSelectedCurrency)
        Log.e("TAG", "calculateCurrencyAmount: $calculateCurrencyResult")
        amountModel.currencyInputAmount = calculateCurrencyResult
        _showCurrencyAmountResult.value = calculateCurrencyResult
    }
}