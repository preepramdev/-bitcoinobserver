package com.pram.bitcoinobserver.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_prices")
data class CoinPriceEntity(
    @PrimaryKey
    val fetchTime: String,
//    @Embedded
//    val time: Time? = null,
//    val disclaimer: String? = null,
//    val chartName: String? = null,
//    @Embedded
//    val bpi: Bpi? = null
) {

//    data class Time(
//        val updated: String? = null,
//        val updatedISO: String? = null,
//        val updateDuk: String? = null
//    )
//
//    data class Bpi(
//        val usd: Currency? = null,
//        val gbp: Currency? = null,
//        val eur: Currency? = null,
//    ) {
//
//        data class Currency(
//            val code: String? = null,
//            val symbol: String? = null,
//            val rate: String? = null,
//            val description: String? = null,
//            val rateFloat: String? = null,
//        )
//    }
}