package com.pram.bitcoinobserver.app.di.global

import com.google.gson.Gson
import org.koin.dsl.module

val utilModule = module {

    single {
        Gson()
    }
}