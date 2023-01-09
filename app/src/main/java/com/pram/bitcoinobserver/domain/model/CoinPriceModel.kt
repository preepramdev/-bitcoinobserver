package com.pram.bitcoinobserver.domain.model

data class CoinPriceModel(
    val fetchTime: String,
    val time: Time? = null,
    val disclaimer: String? = null,
    val chartName: String? = null,
    val bpi: Bpi? = null
) {

    data class Time(
        val updated: String? = null,
        val updatedISO: String? = null,
        val updateDuk: String? = null
    )

    data class Bpi(
        val usd: Currency? = null,
        val gbp: Currency? = null,
        val eur: Currency? = null,
    ) {
        data class Currency(
            val code: String? = null,
            val symbol: String? = null,
            val rate: String? = null,
            val description: String? = null,
            val rateFloat: String? = null,
        )
    }
}
