package com.pram.bitcoinobserver.domain.usecase

import com.pram.bitcoinobserver.data.repository.CoinPriceRepository
import com.pram.bitcoinobserver.data.source.local.entity.CoinPriceEntity
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetHistoryCoinPricesUseCase {

    fun execute(): Flow<List<String>>
}

class GetHistoryCoinPricesUseCaseImpl(
    private val coinPriceRepository: CoinPriceRepository
) : GetHistoryCoinPricesUseCase {

    override fun execute(): Flow<List<String>> {
        return coinPriceRepository.getHistoryPrice()
            .map { coinPriceEntityList ->
                coinPriceEntityList.map {
                    it.fetchTime
                }
            }
    }
}