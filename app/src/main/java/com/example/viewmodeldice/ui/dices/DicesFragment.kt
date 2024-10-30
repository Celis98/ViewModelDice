package com.example.viewmodeldice.ui.dices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.viewmodeldice.R
import com.example.viewmodeldice.databinding.FragmentDicesBinding
import com.example.viewmodeldice.ui.history.HistoryFragment
import com.example.viewmodeldice.ui.viewmodel.DiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DicesFragment : Fragment() {

    private lateinit var binding: FragmentDicesBinding
    private val viewmodel: DiceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDicesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUIStateLifecycle()
        initListeners()
    }

    private fun initListeners() {
        binding.btnRollDice.setOnClickListener {
            viewmodel.rollDice()
        }
        binding.btnHistory.setOnClickListener {
            val action = DicesFragmentDirections.actionDicesFragmentToHistoryFragment(
                isLuckyUser = viewmodel.isLuckyUser(),
                numberOfAttempts = viewmodel.uiState.value.numberOfRolls
            )
            findNavController().navigate(action)
        }
    }

    private fun initUIStateLifecycle() {
        lifecycleScope.launch {
            viewmodel.uiState.collect { uiState ->
                with(binding) {
                    uiState.diceOne?.let {
                        ivDiceOne.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                uiState.diceOne
                            )
                        )
                    }
                    ivDiceTwo.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            uiState.diceTwo
                        )
                    )
                    tvRolls.text = getString(R.string.rolls_attemps, uiState.numberOfRolls)
                    uiState.dicesAreEqual?.let {
                        ivDicesAreEqual.apply {
                            setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    if (uiState.dicesAreEqual == true) R.drawable.ic_check else R.drawable.ic_close
                                )
                            )
                            setColorFilter(
                                ContextCompat.getColor(
                                    requireContext(),
                                    if (uiState.dicesAreEqual == true) R.color.green else R.color.red
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}