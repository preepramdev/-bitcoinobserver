package com.pram.bitcoinobserver.app.di.feature

import com.pram.bitcoinobserver.data.repository.CoinPriceRepository
import com.pram.bitcoinobserver.data.repository.CoinPriceRepositoryImpl
import com.pram.bitcoinobserver.data.source.local.MainDatabase
import com.pram.bitcoinobserver.data.source.local.dao.CoinPriceDao
import com.pram.bitcoinobserver.data.source.remote.CoinDeskApi
import com.pram.bitcoinobserver.domain.usecase.*
import com.pram.bitcoinobserver.presentation.feature.MainViewModel
import com.pram.bitcoinobserver.presentation.feature.converter.ConverterViewModel
import com.pram.bitcoinobserver.presentation.feature.converter.selectcurrency.SelectCurrencyViewModel
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

    factory {
        provideCoinPriceDao(
            mainDatabase = get()
        )
    }

    factory<CoinPriceRepository> {
        CoinPriceRepositoryImpl(
            coinDeskApi = get(),
            coinPriceDao = get()
        )
    }

    factory<GetCurrentCoinPriceUseCase> {
        GetCurrentCoinPriceUseCaseImpl(
            coinPriceRepository = get()
        )
    }

    factory<SaveCoinPriceToHistoryUseCase> {
        SaveCoinPriceToHistoryUseCaseImpl(
            coinPriceRepository = get(),
            gson = get()
        )
    }

    factory<GetHistoryCoinPricesUseCase> {
        GetHistoryCoinPricesUseCaseImpl(
            coinPriceRepository = get(),
            gson = get()
        )
    }

    factory<ConvertCoinToCurrencyUseCase> {
        ConvertCoinToCurrencyUseCaseImpl()
    }

    factory<ConvertCurrencyToCoinUseCase> {
        ConvertCurrencyToCoinUseCaseImpl()
    }

    factory<GetCurrencyCodesUseCase> {
        GetCurrencyCodesUseCaseImpl()
    }

    factory<GetSelectedCurrencyPriceIn1BtcUseCase> {
        GetSelectedCurrencyPriceIn1BtcUseCaseImpl()
    }

    viewModel {
        MainViewModel(
            getCurrentCoinPriceUseCase = get(),
            saveCoinPriceToHistoryUseCase = get()
        )
    }

    viewModel {
        HomeViewModel()
    }

    viewModel {
        ConverterViewModel(
            convertCoinToCurrencyUseCase = get(),
            convertCurrencyToCoinUseCase = get(),
            getSelectedCurrencyPriceIn1BtcUseCase = get()
        )
    }

    viewModel {
        HistoryViewModel(
            getHistoryCoinPricesUseCase = get()
        )
    }

    viewModel {
        SelectCurrencyViewModel(
            getCurrencyCodesUseCase = get()
        )
    }
}

fun provideCoinDeskApi(retrofit: Retrofit): CoinDeskApi {
    return retrofit.create(CoinDeskApi::class.java)
}

fun provideCoinPriceDao(mainDatabase: MainDatabase): CoinPriceDao {
    return mainDatabase.coinPriceDao()
}