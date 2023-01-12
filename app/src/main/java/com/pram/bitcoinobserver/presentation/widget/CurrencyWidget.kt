package com.pram.bitcoinobserver.presentation.widget

import android.content.ClipDescription
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
        tvCurrencyCode.text = currency.code.orEmpty()
        tvDescription.text = HtmlCompat.fromHtml( "${currency.symbol.orEmpty()} ${currency.description.orEmpty()}", 0)
        tvRate.text = HtmlCompat.fromHtml("1 BTC = ${currency.rate} ${currency.symbol}", 0)
    }
}