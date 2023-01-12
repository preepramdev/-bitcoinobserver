package com.pram.bitcoinobserver.domain.usecase.converter

interface ConvertCoinToCurrencyUseCase {

    fun execute(
        coinAmount: Double,
        currencyPriceIn1Btc: Double
    ): Double
}

class ConvertCoinToCurrencyUseCaseImpl : ConvertCoinToCurrencyUseCase {

    override fun execute(coinAmount: Double, currencyPriceIn1Btc: Double): Double {
        return (coinAmount * currencyPriceIn1Btc)
    }
}