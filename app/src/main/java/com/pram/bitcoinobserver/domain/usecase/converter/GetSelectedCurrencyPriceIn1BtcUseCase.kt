package com.pram.bitcoinobserver.domain.usecase.converter

import com.pram.bitcoinobserver.domain.enumModel.CurrencyCodeEnum
import com.pram.bitcoinobserver.domain.model.CoinPriceModel

interface GetSelectedCurrencyPriceIn1BtcUseCase {

    fun execute(
        currencyCodeEnum: CurrencyCodeEnum,
        coinPriceModel: CoinPriceModel?
    ): Double?
}

class GetSelectedCurrencyPriceIn1BtcUseCaseImpl : GetSelectedCurrencyPriceIn1BtcUseCase {

    override fun execute(
        currencyCodeEnum: CurrencyCodeEnum,
        coinPriceModel: CoinPriceModel?
    ): Double? {
        return runCatching {
            val currency = when (currencyCodeEnum) {
                CurrencyCodeEnum.USD -> coinPriceModel?.bpi?.usd
                CurrencyCodeEnum.GBP -> coinPriceModel?.bpi?.gbp
                CurrencyCodeEnum.EUR -> coinPriceModel?.bpi?.eur
            }
            val rate = currency?.rate ?: ""
            rate.replace(",", "").toDoubleOrNull()
        }.getOrNull()
    }
}