package com.pram.bitcoinobserver.presentation.feature.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.pram.bitcoinobserver.databinding.FragmentConverterBinding
import com.pram.bitcoinobserver.presentation.feature.MainViewModel
import com.pram.bitcoinobserver.presentation.feature.converter.extension.toStringIn10Decimal
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment() {

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
            binding.edtBtcAmount.setText(coinAmount.toStringIn10Decimal())
        }
        showCurrencyAmountResult.observe(viewLifecycleOwner) { currencyAmount ->
            binding.edtCurrencyAmount.setText(currencyAmount.toStringIn10Decimal())
        }
    }

    private fun observeMainViewModel() = with(mainViewModel) {
        coinPrice.observe(viewLifecycleOwner) { _coinPrice ->
            binding.apply {
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