package com.pram.bitcoinobserver.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.pram.bitcoinobserver.R
import com.pram.bitcoinobserver.domain.usecase.GetCurrentCoinPriceUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val getCurrentCoinPriceUseCase: GetCurrentCoinPriceUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            getCurrentCoinPriceUseCase.execute()
                .collect()
        }
    }
}