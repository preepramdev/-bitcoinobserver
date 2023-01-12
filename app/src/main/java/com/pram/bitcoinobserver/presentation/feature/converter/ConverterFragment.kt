package com.pram.bitcoinobserver.presentation.feature.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pram.bitcoinobserver.R
import com.pram.bitcoinobserver.databinding.FragmentConverterBinding
import com.pram.bitcoinobserver.domain.enumModel.CurrencyCodeEnum
import com.pram.bitcoinobserver.presentation.feature.MainViewModel
import com.pram.bitcoinobserver.presentation.feature.converter.extension.toStringIn10Decimal
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConverterFragment : Fragment() {

    companion object {
        const val KEY_SELECT_CURRENCY = "KEY_SELECT_CURRENCY"
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
        observeNavControllerResult()
    }

    private fun initView() = with(binding) {
        edtBtcAmount.doAfterTextChanged { text ->
            if (edtBtcAmount.hasFocus()) {
                viewModel.setCoinAmount(text.toString())
            }
        }

        edtCurrencyAmount.doAfterTextChanged { text ->
            if (edtCurrencyAmount.hasFocus()) {
                viewModel.setCurrencyAmount(text.toString())
            }
        }

        tvCurrency.setOnClickListener {
            findNavController().navigate(R.id.action_converterFragment_to_selectCurrencyDialogFragment)
        }
    }

    private fun observeViewModel() = with(viewModel) {
        showCoinAmountResult.observe(viewLifecycleOwner) { coinAmount ->
            binding.edtBtcAmount.setText(coinAmount.toStringIn10Decimal())
        }

        showCurrencyAmountResult.observe(viewLifecycleOwner) { currencyAmount ->
            binding.edtCurrencyAmount.setText(currencyAmount.toStringIn10Decimal())
        }

        showSelectedCurrency.observe(viewLifecycleOwner) { currencyCode ->
            binding.tvCurrency.text = currencyCode.code.uppercase()
        }
    }

    private fun observeMainViewModel() = with(mainViewModel) {
        coinPrice.observe(viewLifecycleOwner) { _coinPrice ->
            binding.apply {
                viewModel.updateCoinPrice(_coinPrice)
                if (edtBtcAmount.hasFocus()) {
                    viewModel.setCoinAmount(edtBtcAmount.text.toString())
                }
                if (edtCurrencyAmount.hasFocus()) {
                    viewModel.setCurrencyAmount(edtCurrencyAmount.text.toString())
                }
            }
        }
    }

    private fun observeNavControllerResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.apply {
            getLiveData<CurrencyCodeEnum>(KEY_SELECT_CURRENCY)
                .observe(viewLifecycleOwner) { currencyCode ->
                    viewModel.selectedCurrencyCode(currencyCode)
                }
        }
    }
}