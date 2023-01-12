package com.pram.bitcoinobserver.app.di.global

import android.content.Context
import androidx.room.Room
import com.pram.bitcoinobserver.data.source.local.MainDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        provideMainDatabase(
            context = androidContext()
        )
    }
}

fun provideMainDatabase(context: Context): MainDatabase {
    return Room.databaseBuilder(
        context,
        MainDatabase::class.java,
        "main_database"
    ).build()
}