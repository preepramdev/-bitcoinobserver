package com.pram.bitcoinobserver.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CurrentPriceResponse(
    @SerializedName("time")
    val time: Time? = null,
    @SerializedName("disclaimer")
    val disclaimer: String? = null,
    @SerializedName("chartName")
    val chartName: String? = null,
    @SerializedName("bpi")
    val bpi: Bpi? = null
) {

    data class Time(
        @SerializedName("updated")
        val updated: String? = null,
        @SerializedName("updatedISO")
        val updatedISO: String? = null,
        @SerializedName("updateduk")
        val updateDuk: String? = null
    )

    data class Bpi(
        @SerializedName("USD")
        val usd: Currency? = null,
        @SerializedName("GBP")
        val gbp: Currency? = null,
        @SerializedName("EUR")
        val eur: Currency? = null,
    ) {
        data class Currency(
            @SerializedName("code")
            val code: String? = null,
            @SerializedName("symbol")
            val symbol: String? = null,
            @SerializedName("rate")
            val rate: String? = null,
            @SerializedName("description")
            val description: String? = null,
            @SerializedName("rate_float")
            val rateFloat: String? = null,
        )
    }
}
