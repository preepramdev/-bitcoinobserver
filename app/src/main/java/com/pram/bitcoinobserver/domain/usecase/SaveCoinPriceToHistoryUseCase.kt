package com.pram.bitcoinobserver.domain.usecase

import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import kotlinx.coroutines.flow.Flow

interface SaveCoinPriceToHistoryUseCase {

    fun execute(coinPriceModel: CoinPriceModel): Flow<Unit>
}