package com.example.viewmodeldice.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.viewmodeldice.data.entities.ResultEntity
import com.example.viewmodeldice.databinding.FragmentHistoryBinding
import com.example.viewmodeldice.databinding.ViewResultBinding
import com.example.viewmodeldice.ui.viewmodel.DiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val viewmodel: DiceViewModel by activityViewModels()
    private val args: HistoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateLuckyUser()
        loadHistory()
        initListeners()
        initUIStateLifecycle()
    }

    private fun validateLuckyUser() {
        binding.tvLuckyUser.visibility = if (args.isLuckyUser) VISIBLE else GONE
        if (args.isLuckyUser) viewmodel.activateGoodLock()
    }

    private fun initUIStateLifecycle() {
        lifecycleScope.launch {
            viewmodel.uiState.collect { uiState ->
                refreshHistory(uiState.historyResults)
            }
        }
    }

    private fun refreshHistory(historyResults: List<ResultEntity>) {
        binding.llResults.apply {
            removeAllViews()
            historyResults.forEach { result ->
                val resultView = ViewResultBinding.inflate(
                    LayoutInflater.from(this.context),
                    this,
                    true
                )
                resultView.tvResult.text = "${result.diceOne} - ${result.diceTwo}"
            }
        }
    }

    private fun loadHistory() {
        viewmodel.refreshHistory()
        binding.tvAttemps.text = "Attemps: ${args.numberOfAttempts}"
    }

    private fun initListeners() {
        binding.btnGoBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}