package com.pram.bitcoinobserver.domain.usecase.converter

interface ConvertCurrencyToCoinUseCase {

    fun execute(
        currencyAmount: Double,
        currencyPriceIn1Btc: Double
    ): Double
}

class ConvertCurrencyToCoinUseCaseImpl : ConvertCurrencyToCoinUseCase {

    override fun execute(currencyAmount: Double, currencyPriceIn1Btc: Double): Double {
        return (currencyAmount / currencyPriceIn1Btc)
    }
}