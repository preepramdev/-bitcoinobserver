package com.pram.bitcoinobserver.presentation

import java.text.SimpleDateFormat

fun String.formatDate(originalPattern: String, newPattern: String): String {
    try {
        val inputFormat = SimpleDateFormat(originalPattern)
        val outputFormat = SimpleDateFormat(newPattern)
        val date = inputFormat.parse(this)
        val outputDate = outputFormat.format(date)
        println(outputDate) // should print "14-01-2023 14:24"
        return outputDate
    } catch (e: Exception) {
        e.printStackTrace()
        return this
    }
}