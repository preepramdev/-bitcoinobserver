package com.pram.bitcoinobserver.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pram.bitcoinobserver.data.source.local.entity.CoinPriceEntity

@Dao
interface CoinPriceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCoinPrice(coinPriceEntity: CoinPriceEntity)

    @Query("SELECT * FROM coin_prices")
    fun getCoinPrice(): List<CoinPriceEntity>
}