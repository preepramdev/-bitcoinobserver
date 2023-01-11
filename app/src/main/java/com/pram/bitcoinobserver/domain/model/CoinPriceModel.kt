package com.pram.bitcoinobserver.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CoinPriceModel(
    val fetchTime: String,
    val time: Time? = null,
    val disclaimer: String? = null,
    val chartName: String? = null,
    val bpi: Bpi? = null
): Parcelable {

    @Parcelize
    data class Time(
        val updated: String? = null,
        val updatedISO: String? = null,
        val updateDuk: String? = null
    ): Parcelable

    @Parcelize
    data class Bpi(
        val usd: Currency? = null,
        val gbp: Currency? = null,
        val eur: Currency? = null,
    ): Parcelable {

        @Parcelize
        data class Currency(
            val code: String? = null,
            val symbol: String? = null,
            val rate: String? = null,
            val description: String? = null,
            val rateFloat: String? = null,
        ): Parcelable
    }
}
