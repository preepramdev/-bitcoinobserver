package com.pram.bitcoinobserver.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pram.bitcoinobserver.databinding.DialogHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryDialogFragment : Fragment() {

    private val binding by lazy { DialogHistoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<HistoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}