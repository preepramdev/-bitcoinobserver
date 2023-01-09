package com.pram.bitcoinobserver.data.repository

import android.util.Log
import com.pram.bitcoinobserver.data.source.local.dao.CoinPriceDao
import com.pram.bitcoinobserver.data.source.local.entity.CoinPriceEntity
import com.pram.bitcoinobserver.data.source.remote.CoinDeskApi
import com.pram.bitcoinobserver.data.source.remote.response.CurrentPriceResponse
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

interface CoinPriceRepository {

    fun getCurrentPrice(): Flow<CurrentPriceResponse>
    fun getHistoryPrice(): Flow<List<CoinPriceEntity>>
    fun saveCoinPrice(coinPriceEntity: CoinPriceEntity): Flow<Unit>
}

class CoinPriceRepositoryImpl (
    private val coinDeskApi: CoinDeskApi,
    private val coinPriceDao: CoinPriceDao
): CoinPriceRepository {

    override fun getCurrentPrice(): Flow<CurrentPriceResponse> = flow {
        val response = coinDeskApi.getCurrentPrice()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                emit(body)
            } ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    override fun getHistoryPrice(): Flow<List<CoinPriceEntity>> = flow {
        runCatching {
            coinPriceDao.getCoinPrice()
        }.onSuccess { coinPriceEntityList ->
            emit(coinPriceEntityList)
        }.onFailure { exception ->
            exception.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

    override fun saveCoinPrice(coinPriceEntity: CoinPriceEntity): Flow<Unit> = flow {
        runCatching {
            coinPriceDao.saveCoinPrice(coinPriceEntity)
        }.onSuccess {
            emit(Unit)
        }.onFailure { exception ->
            exception.printStackTrace()
            emit(Unit)
        }
    }.flowOn(Dispatchers.IO)


}