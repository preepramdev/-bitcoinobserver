package com.pram.bitcoinobserver.domain.usecase.converter

import com.pram.bitcoinobserver.domain.enumModel.CurrencyCodeEnum

interface GetCurrencyCodesUseCase {

    fun execute(): List<CurrencyCodeEnum>
}

class GetCurrencyCodesUseCaseImpl : GetCurrencyCodesUseCase {

    override fun execute(): List<CurrencyCodeEnum> {
        return CurrencyCodeEnum.values().toList()
    }
}

