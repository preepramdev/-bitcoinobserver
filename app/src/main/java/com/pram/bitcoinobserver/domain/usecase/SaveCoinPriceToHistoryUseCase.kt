package com.pram.bitcoinobserver.domain.usecase

import android.util.Log
import com.pram.bitcoinobserver.data.repository.CoinPriceRepository
import com.pram.bitcoinobserver.data.source.local.entity.CoinPriceEntity
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import kotlinx.coroutines.flow.Flow

interface SaveCoinPriceToHistoryUseCase {

    fun execute(coinPriceModel: CoinPriceModel): Flow<Unit>
}

class SaveCoinPriceToHistoryUseCaseImpl(
    private val coinPriceRepository: CoinPriceRepository
): SaveCoinPriceToHistoryUseCase {

    override fun execute(coinPriceModel: CoinPriceModel): Flow<Unit> {
        Log.e("TAG", "execute: ", )
        val coinPriceEntity = CoinPriceEntity(
            fetchTime = coinPriceModel.fetchTime
        )
        Log.e("TAG", "execute: $coinPriceEntity", )
        return coinPriceRepository.saveCoinPrice(coinPriceEntity)
    }
}