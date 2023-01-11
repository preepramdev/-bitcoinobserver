package com.pram.bitcoinobserver.presentation.feature.converter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.pram.bitcoinobserver.databinding.FragmentConverterBinding
import com.pram.bitcoinobserver.presentation.feature.MainViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment() {

    companion object {

    }

    private val binding by lazy { FragmentConverterBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<ConverterViewModel>()
    private val mainViewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeViewModel()
        observeMainViewModel()
    }

    private fun initView() = with(binding) {
        edtBtcAmount.doAfterTextChanged { text ->
            if (edtBtcAmount.hasFocus()) {
                viewModel.setCoinAmount(text.toString())
            }
        }

        edtCurrencyAmount.doAfterTextChanged { text ->
            if (edtCurrencyAmount.hasFocus()) {
                viewModel.setCurrencyAmount(text.toString(), "usd")
            }
        }
    }

    private fun observeViewModel() = with(viewModel) {
        showCoinAmountResult.observe(viewLifecycleOwner) { coinAmount ->
            lifecycleScope.launchWhenResumed {
                binding.edtBtcAmount.setText(String.format("%.10f", coinAmount))
                delay(1000L)
                binding.edtCurrencyAmount.clearFocus()
            }
        }
        showCurrencyAmountResult.observe(viewLifecycleOwner) { currencyAmount ->
            lifecycleScope.launchWhenResumed {
                binding.edtCurrencyAmount.setText(String.format("%.10f", currencyAmount))
                delay(1000L)
                binding.edtBtcAmount.clearFocus()
            }
        }
    }

    private fun observeMainViewModel() = with(mainViewModel) {
        coinPrice.observe(viewLifecycleOwner) { _coinPrice ->
            binding.apply {
                if (!edtBtcAmount.hasFocus() && !edtCurrencyAmount.hasFocus()) {
                    viewModel.setCoinPrice(_coinPrice)
                    if (edtBtcAmount.hasFocus()) {
                        viewModel.setCoinAmount(edtBtcAmount.text.toString())
                    }
                    if (edtCurrencyAmount.hasFocus()) {
                        viewModel.setCurrencyAmount(edtCurrencyAmount.text.toString(), "usd")
                    }
                }
            }
        }
    }
}