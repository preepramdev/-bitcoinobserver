package com.pram.bitcoinobserver.domain.usecase.main

import android.annotation.SuppressLint
import com.pram.bitcoinobserver.data.repository.CoinPriceRepository
import com.pram.bitcoinobserver.data.source.remote.response.CurrentPriceResponse
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

interface GetCurrentCoinPriceUseCase {

    fun execute(): Flow<CoinPriceModel>
}

class GetCurrentCoinPriceUseCaseImpl(
    private val coinPriceRepository: CoinPriceRepository
): GetCurrentCoinPriceUseCase {

    override fun execute(): Flow<CoinPriceModel> {
        return coinPriceRepository.getCurrentPrice()
            .map { currentPriceResponse ->
                mapCurrentPriceResponseToCoinPriceModel(currentPriceResponse)
            }
    }

    @SuppressLint("NewApi") // todo
    private fun mapCurrentPriceResponseToCoinPriceModel(
        currentPriceResponse: CurrentPriceResponse
    ): CoinPriceModel {

        val time = currentPriceResponse.time
        val bpi = currentPriceResponse.bpi

        return CoinPriceModel(
            fetchTime = LocalDateTime.now().toString(),
            time = CoinPriceModel.Time(
                updated = time?.updated,
                updatedISO = time?.updatedISO,
                updateDuk = time?.updateDuk
            ),
            disclaimer = currentPriceResponse.disclaimer,
            chartName = currentPriceResponse.chartName,
            bpi = CoinPriceModel.Bpi(
                usd = CoinPriceModel.Bpi.Currency(
                    code = bpi?.usd?.code,
                    symbol = bpi?.usd?.symbol,
                    rate = bpi?.usd?.rate,
                ),
                gbp = CoinPriceModel.Bpi.Currency(
                    code = bpi?.gbp?.code,
                    symbol = bpi?.gbp?.symbol,
                    rate = bpi?.gbp?.rate,
                ),
                eur = CoinPriceModel.Bpi.Currency(
                    code = bpi?.eur?.code,
                    symbol = bpi?.eur?.symbol,
                    rate = bpi?.eur?.rate,
                ),
            )
        )
    }
}