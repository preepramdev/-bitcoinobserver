package com.pram.bitcoinobserver.presentation.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pram.bitcoinobserver.R
import com.pram.bitcoinobserver.databinding.ActivityMainBinding
import com.pram.bitcoinobserver.domain.model.CoinPriceModel
import com.pram.bitcoinobserver.presentation.formatDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        //                private const val DELAY_UPDATE_TIME = 1000L * 60
        private const val DELAY_UPDATE_TIME = 2000L
        private const val FETCH_TIME_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS"
        private const val UPDATE_TIME_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX"
        private const val DISPLAY_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss"
        const val KET_SELECT_FROM_HISTORY = "KET_SELECT_FROM_HISTORY"
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<MainViewModel>()

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHomeFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHomeFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)

        initView()
        observeViewModel()
        observeNavControllerResult()
        viewModel.refreshCoinPrice()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {
            while (isActive) {
                delay(DELAY_UPDATE_TIME)
                viewModel.refreshCoinPrice()
            }
        }
    }

    private fun initView() = with(binding) {
        swLive.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setIsLiveNeeded(isChecked)
        }

        btnHistory.setOnClickListener {
            navController.navigate(R.id.action_mainActivity_to_historyDialogFragment)
        }
    }

    private fun observeViewModel() = with(viewModel) {
        isLive.observe(this@MainActivity) { _isLive ->
            binding.swLive.isChecked = _isLive
        }

        coinPrice.observe(this@MainActivity) { _coinPrice ->
            setDisclaimerData(_coinPrice)
        }
    }

    private fun observeNavControllerResult() {
        navController.currentBackStackEntry?.savedStateHandle?.apply {
            getLiveData<CoinPriceModel>(KET_SELECT_FROM_HISTORY)
                .observe(this@MainActivity) { selectedCoinPrice ->
                    viewModel.setCoinPriceFromHistory(selectedCoinPrice)
                }
        }
    }

    private fun setDisclaimerData(_coinPrice: CoinPriceModel) {
        binding.apply {
            val fetchTime = _coinPrice.fetchTime.formatDate(
                FETCH_TIME_DATE_FORMAT,
                DISPLAY_DATE_FORMAT
            )
            val updateTime = _coinPrice.time?.updatedISO?.formatDate(
                UPDATE_TIME_DATE_FORMAT,
                DISPLAY_DATE_FORMAT
            )
            tvFetchTime.text = fetchTime
            tvUpdateTime.text = updateTime
            tvDisclaimer.text = _coinPrice.disclaimer.orEmpty()
        }
    }
}