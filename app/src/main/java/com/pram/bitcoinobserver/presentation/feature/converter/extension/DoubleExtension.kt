package com.pram.bitcoinobserver.presentation.feature.converter.extension

fun Double.toStringIn10Decimal(): String {
    return String.format("%.10f", this)
}