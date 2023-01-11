package com.pram.bitcoinobserver.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_prices")
data class CoinPriceEntity(
    @PrimaryKey
    val fetchTime: String,
    val coinPriceJson: String
)