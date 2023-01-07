package com.pram.bitcoinobserver.data.source.remote

import com.pram.bitcoinobserver.data.source.remote.response.CurrentPriceResponse
import retrofit2.Response
import retrofit2.http.GET

interface CoinDeskApi {

    @GET("/v1/bpi/currentprice.json")
    suspend fun getCurrentPrice(): Response<CurrentPriceResponse>
}