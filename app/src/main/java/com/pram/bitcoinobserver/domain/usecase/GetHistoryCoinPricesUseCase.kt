package com.pram.bitcoinobserver.domain.usecase

import com.google.gson.Gson
import com.pram.bitcoinobserver.data.repository.CoinPriceRepository
import com.pram.bitcoinobserver.data.source.local.entity.CoinPriceEntity
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetHistoryCoinPricesUseCase {

    fun execute(): Flow<List<CoinPriceModel>>
}

class GetHistoryCoinPricesUseCaseImpl(
    private val coinPriceRepository: CoinPriceRepository,
    private val gson: Gson
) : GetHistoryCoinPricesUseCase {

    override fun execute(): Flow<List<CoinPriceModel>> {
        return coinPriceRepository.getHistoryPrice()
            .map { coinPriceEntityList ->
                coinPriceEntityList.map { coinPriceEntity ->
                    gson.fromJson(coinPriceEntity.coinPriceJson, CoinPriceModel::class.java)
                }
            }
    }
}