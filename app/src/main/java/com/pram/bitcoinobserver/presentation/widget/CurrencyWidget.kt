package com.pram.bitcoinobserver.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.text.HtmlCompat
import com.pram.bitcoinobserver.databinding.WidgetCurrencyBinding
import com.pram.bitcoinobserver.domain.model.CoinPriceModel

class CurrencyWidget@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = WidgetCurrencyBinding.inflate(
        LayoutInflater.from(context),
        this@CurrencyWidget,
        true
    )

    fun setCurrency(currency: CoinPriceModel.Bpi.Currency) = with(binding) {
        val description = HtmlCompat.fromHtml(
            "${currency.symbol.orEmpty()} ${currency.description.orEmpty()}",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        val rate = HtmlCompat.fromHtml(
            "1 BTC = ${currency.rate} ${currency.symbol}",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        tvCurrencyCode.text = currency.code.orEmpty()
        tvDescription.text = description
        tvRate.text = rate
    }
}