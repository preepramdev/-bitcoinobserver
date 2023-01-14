package com.pram.bitcoinobserver.presentation.feature.converter.selectcurrency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pram.bitcoinobserver.databinding.DialogSelectCurrencyBinding
import com.pram.bitcoinobserver.presentation.feature.converter.ConverterFragment
import com.pram.bitcoinobserver.presentation.feature.converter.selectcurrency.adapter.CurrencyItemAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCurrencyDialogFragment : DialogFragment() {

    private val binding by lazy { DialogSelectCurrencyBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<SelectCurrencyViewModel>()

    private val currencyItemAdapter = CurrencyItemAdapter()

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
        viewModel.getCurrencyCodes()
    }

    private fun initView() = with(binding) {
        currencyItemAdapter.apply {
            onItemClicked = { currencyCodeEnum ->
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    ConverterFragment.KEY_SELECT_CURRENCY,
                    currencyCodeEnum
                )
                dismiss()
            }
        }

        rvCurrencies.apply {
            adapter = currencyItemAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() = with(viewModel) {
        showCurrencyCode.observe(viewLifecycleOwner) { currencyCodes ->
            currencyItemAdapter.setupCurrencyList(currencyCodes)
        }
    }
}