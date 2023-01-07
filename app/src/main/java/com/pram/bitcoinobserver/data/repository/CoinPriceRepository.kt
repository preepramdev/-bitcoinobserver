package com.pram.bitcoinobserver.data.repository

import com.pram.bitcoinobserver.data.source.remote.CoinDeskApi
import com.pram.bitcoinobserver.data.source.remote.response.CurrentPriceResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

interface CoinPriceRepository {

    fun getCurrentPrice(): Flow<CurrentPriceResponse>
    fun getHistoryPrice(): Flow<Unit>
}

class CoinPriceRepositoryImpl (
    private val coinDeskApi: CoinDeskApi
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

    override fun getHistoryPrice(): Flow<Unit> = flow {

    }
}