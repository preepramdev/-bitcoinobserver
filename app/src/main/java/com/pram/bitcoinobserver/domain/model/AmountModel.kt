package com.pram.bitcoinobserver.domain.model

import com.pram.bitcoinobserver.domain.enumModel.CurrencyCodeEnum

data class AmountModel(
    var coinInputAmount: Double = 0.0,
    var currencyInputAmount: Double = 0.0,
    var currencyCode: CurrencyCodeEnum = CurrencyCodeEnum.USD
)
