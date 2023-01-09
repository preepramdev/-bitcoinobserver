package com.pram.bitcoinobserver.domain.usecase

import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import kotlinx.coroutines.flow.Flow

interface GetHistoryCoinPricesUseCase {

    fun execute(): Flow<List<CoinPriceModel>>
}