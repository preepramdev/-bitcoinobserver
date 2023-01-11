package com.pram.bitcoinobserver.app.di

import com.pram.bitcoinobserver.app.di.feature.mainModule
import com.pram.bitcoinobserver.app.di.global.databaseModule
import com.pram.bitcoinobserver.app.di.global.networkModule
import com.pram.bitcoinobserver.app.di.global.utilModule

val appModules = listOf(
    databaseModule,
    mainModule,
    networkModule,
    utilModule
)