package com.pram.bitcoinobserver.presentation.feature.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.pram.bitcoinobserver.databinding.DialogHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryDialogFragment : DialogFragment() {

    private val binding by lazy { DialogHistoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() = with(viewModel) {

    }
}