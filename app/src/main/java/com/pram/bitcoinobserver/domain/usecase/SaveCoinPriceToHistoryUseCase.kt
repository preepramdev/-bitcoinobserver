package com.pram.bitcoinobserver.domain.usecase

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
        val coinPriceEntity = CoinPriceEntity(
            fetchTime = coinPriceModel.fetchTime
        )
        return coinPriceRepository.saveCoinPrice(coinPriceEntity)
    }
}