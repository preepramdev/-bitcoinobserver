package com.pram.bitcoinobserver.presentation.feature.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pram.bitcoinobserver.domain.enumModel.CurrencyCodeEnum
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import com.pram.bitcoinobserver.domain.model.ConvertAmountModel
import com.pram.bitcoinobserver.domain.usecase.converter.ConvertCoinToCurrencyUseCase
import com.pram.bitcoinobserver.domain.usecase.converter.ConvertCurrencyToCoinUseCase
import com.pram.bitcoinobserver.domain.usecase.converter.GetSelectedCurrencyPriceIn1BtcUseCase

class ConverterViewModel(
    private val convertCoinToCurrencyUseCase: ConvertCoinToCurrencyUseCase,
    private val convertCurrencyToCoinUseCase: ConvertCurrencyToCoinUseCase,
    private val getSelectedCurrencyPriceIn1BtcUseCase: GetSelectedCurrencyPriceIn1BtcUseCase
) : ViewModel() {

    companion object {
        private const val DEFAULT_INPUT_AMOUNT = 0.0
        private const val DEFAULT_COIN_PRICE_IN_SELECTED_CURRENCY = 0.0
    }

    private var convertAmountModel = ConvertAmountModel()
    private var coinPrice: CoinPriceModel? = null
    private var selectedCurrencyIn1Btc: Double = 0.0

    private val _showCoinAmountResult = MutableLiveData<Double>()
    private val _showCurrencyAmountResult = MutableLiveData<Double>()
    private val _showSelectedCurrency = MutableLiveData<CurrencyCodeEnum>()

    val showCoinAmountResult: LiveData<Double> = _showCoinAmountResult
    val showCurrencyAmountResult: LiveData<Double> = _showCurrencyAmountResult
    val showSelectedCurrency: LiveData<CurrencyCodeEnum> = _showSelectedCurrency

    fun selectedCurrencyCode(currencyCodeEnum: CurrencyCodeEnum) {
        convertAmountModel.currencyCode = currencyCodeEnum
        calculateSelectedCurrencyPriceIn1Btc()
        calculateCurrencyAmount()
        _showSelectedCurrency.value = convertAmountModel.currencyCode
    }

    fun updateCoinPrice(coinPrice: CoinPriceModel) {
        this.coinPrice = coinPrice
        calculateSelectedCurrencyPriceIn1Btc()
    }

    fun setCoinAmount(coinInput: String) {
        runCatching {
            coinInput.toDoubleOrNull() ?: DEFAULT_INPUT_AMOUNT
        }.onSuccess { coinAmount ->
            convertAmountModel.coinInputAmount = coinAmount
            calculateCurrencyAmount()
        }.onFailure { exception ->
            exception.printStackTrace()
        }
    }

    fun setCurrencyAmount(currencyInput: String) {
        runCatching {
            currencyInput.toDoubleOrNull() ?: DEFAULT_INPUT_AMOUNT
        }.onSuccess { currencyAmount ->
            convertAmountModel.currencyInputAmount = currencyAmount
            calculateCoinAmount()
        }.onFailure { exception ->
            exception.printStackTrace()
        }
    }

    private fun calculateSelectedCurrencyPriceIn1Btc() {
        selectedCurrencyIn1Btc = getSelectedCurrencyPriceIn1BtcUseCase.execute(
            convertAmountModel.currencyCode,
            coinPrice
        ) ?: DEFAULT_COIN_PRICE_IN_SELECTED_CURRENCY
    }

    private fun calculateCoinAmount() {
        val calculateCoinResult = convertCurrencyToCoinUseCase.execute(
            convertAmountModel.currencyInputAmount,
            selectedCurrencyIn1Btc
        )
        convertAmountModel.coinInputAmount = calculateCoinResult
        _showCoinAmountResult.value = calculateCoinResult
    }

    private fun calculateCurrencyAmount() {
        val calculateCurrencyResult = convertCoinToCurrencyUseCase.execute(
            convertAmountModel.coinInputAmount,
            selectedCurrencyIn1Btc
        )
        convertAmountModel.currencyInputAmount = calculateCurrencyResult
        _showCurrencyAmountResult.value = calculateCurrencyResult
    }
}