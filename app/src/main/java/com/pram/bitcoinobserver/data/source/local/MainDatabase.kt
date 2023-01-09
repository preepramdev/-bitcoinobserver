package com.pram.bitcoinobserver.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pram.bitcoinobserver.data.source.local.dao.CoinPriceDao
import com.pram.bitcoinobserver.data.source.local.entity.CoinPriceEntity

@Database(entities = [CoinPriceEntity::class], version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun coinPriceDao(): CoinPriceDao
}