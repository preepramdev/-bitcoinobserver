package com.pram.bitcoinobserver.presentation.extension

import java.text.SimpleDateFormat

fun String.formatDate(originalPattern: String, newPattern: String): String {
    return try {
        val inputFormat = SimpleDateFormat(originalPattern)
        val outputFormat = SimpleDateFormat(newPattern)
        val date = inputFormat.parse(this)
        val outputDate = outputFormat.format(date)
        outputDate
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}