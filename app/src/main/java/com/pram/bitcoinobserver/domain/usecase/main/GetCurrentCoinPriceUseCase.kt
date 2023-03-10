package com.pram.bitcoinobserver.domain.usecase.main

import android.annotation.SuppressLint
import com.pram.bitcoinobserver.data.repository.CoinPriceRepository
import com.pram.bitcoinobserver.data.source.remote.response.CurrentPriceResponse
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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

    @SuppressLint("NewApi")
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
                    description = bpi?.usd?.description,
                    rate = bpi?.usd?.rate,
                ),
                gbp = CoinPriceModel.Bpi.Currency(
                    code = bpi?.gbp?.code,
                    symbol = bpi?.gbp?.symbol,
                    description = bpi?.gbp?.description,
                    rate = bpi?.gbp?.rate,
                ),
                eur = CoinPriceModel.Bpi.Currency(
                    code = bpi?.eur?.code,
                    symbol = bpi?.eur?.symbol,
                    description = bpi?.eur?.description,
                    rate = bpi?.eur?.rate,
                ),
            )
        )
    }
}