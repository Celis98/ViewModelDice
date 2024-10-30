package com.example.viewmodeldice.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodeldice.R
import com.example.viewmodeldice.data.db.ResultsDao
import com.example.viewmodeldice.data.entities.ResultEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiceViewModel @Inject constructor(
    private val resultsDao: ResultsDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiceUIState())
    val uiState: StateFlow<DiceUIState> = _uiState.asStateFlow()
    private var isGoodLuckActive = false
    private var goodLuckCounter = 0

    private var dicesList: List<Int> = listOf(
        R.drawable.dice_one,
        R.drawable.dice_two,
        R.drawable.dice_three,
        R.drawable.dice_four,
        R.drawable.dice_five,
        R.drawable.dice_six
    )

    fun rollDice() {
        val diceOne = obtainDice()
        val diceTwo = if (isGoodLuckActive) diceOne else obtainDice()
        if (isGoodLuckActive) goodLuckCounter++
        if (goodLuckCounter == 5) resetGoodLuck()
        _uiState.value = _uiState.value.copy(
            diceOne = diceOne,
            diceTwo = diceTwo,
            numberOfRolls = _uiState.value.numberOfRolls + 1,
            dicesAreEqual = diceOne == diceTwo
        )
        saveResult(diceOne, diceTwo)
    }

    private fun resetGoodLuck() {
        isGoodLuckActive = false
        goodLuckCounter = 0
    }

    fun refreshHistory() {
        viewModelScope.launch {
            val results = resultsDao.getAllResults()
            _uiState.update { it.copy(historyResults = results) }
        }
    }

    fun activateGoodLock() {
        isGoodLuckActive = true
    }

    fun isLuckyUser(): Boolean =
        _uiState.value.diceOne == _uiState.value.diceTwo

    private fun saveResult(diceOne: Int, diceTwo: Int) {
        viewModelScope.launch {
            val results = resultsDao.getAllResults()
            val newResult =
                ResultEntity(
                    diceOne = dicesList.indexOf(diceOne),
                    diceTwo = dicesList.indexOf(diceTwo)
                )
            if (results.size <= 5) {
                resultsDao.insertResult(newResult)
            } else {
                resultsDao.deleteResult(results.first())
                resultsDao.insertResult(newResult)
            }
        }
    }

    private fun obtainDice() : Int =
        dicesList.random()

}