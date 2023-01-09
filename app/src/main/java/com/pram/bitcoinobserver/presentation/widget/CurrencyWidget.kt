package com.pram.bitcoinobserver.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.pram.bitcoinobserver.databinding.WidgetCurrencyBinding

class CurrencyWidget@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = WidgetCurrencyBinding.inflate(
        LayoutInflater.from(context),
        this@CurrencyWidget,
        true
    )

    fun setCurrencyCode(code: String) {
        binding.tvCurrencyCode.text = "currency : $code"
    }

    fun setSymbol(symbol: String) {
        binding.tvSymbol.text = "symbol : $symbol"
    }

    fun setDescription(description: String) {
        binding.tvDescription.text = "description : $description"
    }

    fun setRate(rate: String) {
        binding.tvRate.text = "rate : $rate"
    }
}