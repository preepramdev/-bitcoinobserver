package com.pram.bitcoinobserver.data.repository

import com.pram.bitcoinobserver.data.source.local.dao.CoinPriceDao
import com.pram.bitcoinobserver.data.source.local.entity.CoinPriceEntity
import com.pram.bitcoinobserver.data.source.remote.CoinDeskApi
import com.pram.bitcoinobserver.data.source.remote.response.CurrentPriceResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class CoinPriceRepositoryTest {

    private lateinit var coinPriceRepository: CoinPriceRepository
    private val coinDeskApi: CoinDeskApi = mockk()
    private val coinPriceDao: CoinPriceDao = mockk()

    @Before
    fun setup() {
        coinPriceRepository = CoinPriceRepositoryImpl(coinDeskApi, coinPriceDao)
    }

    @Test
    fun `get CurrentPriceResponse when getCurrentPrice success`() = runTest {

        val currentPriceResponse = CurrentPriceResponse()
        val response = Response.success(200, currentPriceResponse)

        coEvery {
            coinDeskApi.getCurrentPrice()
        } returns response

        val result = coinPriceRepository.getCurrentPrice()

        result.collectLatest { _currentPriceResponse ->
            assert(_currentPriceResponse == currentPriceResponse)
        }
    }

    @Test
    fun `get HttpException when getCurrentPrice fail`() = runTest {

        val responseText = "{}"

        val responseBody = responseText.toResponseBody("application/json".toMediaTypeOrNull())
        val response = Response.error<CurrentPriceResponse>(500, responseBody)

        coEvery {
            coinDeskApi.getCurrentPrice()
        } returns response

        val result = coinPriceRepository.getCurrentPrice()

        result.catch { exception ->
            assert(exception is HttpException)
        }.collect()
    }

    @Test
    fun `get List CoinPriceEntity when getHistoryPrice success`() = runTest {

        val coinPriceEntityList = listOf(
            CoinPriceEntity(
                fetchTime = "2023-01-15",
                coinPriceJson = "{}"
            ),
            CoinPriceEntity(
                fetchTime = "2023-01-14",
                coinPriceJson = "{}"
            ),
            CoinPriceEntity(
                fetchTime = "2023-01-13",
                coinPriceJson = "{}"
            )
        )

        every {
            coinPriceDao.getCoinPrice()
        } returns coinPriceEntityList

        val result = coinPriceRepository.getHistoryPrice()

        result.collectLatest { _coinPriceEntityList ->
            assert(_coinPriceEntityList == coinPriceEntityList)
        }
    }

    @Test
    fun `get Exception when getHistoryPrice fail`() = runTest {

        every {
            coinPriceDao.getCoinPrice()
        } throws IOException()

        val result = coinPriceRepository.getHistoryPrice()

        result.catch { exception ->
            assert(exception is IOException)
        }.collect()
    }

    @Test
    fun `get Unit when saveCoinPrice success`() = runTest {

        val coinPriceEntity = CoinPriceEntity(
            fetchTime = "2023-01-15",
            coinPriceJson = "{}"
        )

        every {
            coinPriceDao.saveCoinPrice(coinPriceEntity)
        } returns Unit

        val result = coinPriceRepository.saveCoinPrice(coinPriceEntity)

        val collectResult = runCatching { result.collect() }
        assert(collectResult.isSuccess)
    }

    @Test
    fun `get Exception when saveCoinPrice fail`() = runTest {

        val coinPriceEntity = CoinPriceEntity(
            fetchTime = "2023-01-15",
            coinPriceJson = "{}"
        )

        every {
            coinPriceDao.saveCoinPrice(coinPriceEntity)
        } throws IOException()

        val result = coinPriceRepository.saveCoinPrice(coinPriceEntity)

        result.catch { exception ->
            assert(exception is IOException)
        }.collect()
    }
}