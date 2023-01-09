package com.pram.bitcoinobserver.app.di.feature

import com.pram.bitcoinobserver.data.repository.CoinPriceRepository
import com.pram.bitcoinobserver.data.repository.CoinPriceRepositoryImpl
import com.pram.bitcoinobserver.data.source.remote.CoinDeskApi
import com.pram.bitcoinobserver.domain.usecase.GetCurrentCoinPriceUseCase
import com.pram.bitcoinobserver.domain.usecase.GetCurrentCoinPriceUseCaseImpl
import com.pram.bitcoinobserver.presentation.feature.MainViewModel
import com.pram.bitcoinobserver.presentation.feature.converter.ConverterViewModel
import com.pram.bitcoinobserver.presentation.feature.history.HistoryViewModel
import com.pram.bitcoinobserver.presentation.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mainModule = module {

    factory {
        provideCoinDeskApi(
            retrofit = get()
        )
    }

    factory<CoinPriceRepository> {
        CoinPriceRepositoryImpl(
            coinDeskApi = get()
        )
    }

    factory<GetCurrentCoinPriceUseCase> {
        GetCurrentCoinPriceUseCaseImpl(
            coinPriceRepository = get()
        )
    }

    viewModel {
        MainViewModel()
    }

    viewModel {
        HomeViewModel(
            getCurrentCoinPriceUseCase = get()
        )
    }

    viewModel {
        ConverterViewModel()
    }

    viewModel {
        HistoryViewModel()
    }
}

fun provideCoinDeskApi(retrofit: Retrofit): CoinDeskApi {
    return retrofit.create(CoinDeskApi::class.java)
}