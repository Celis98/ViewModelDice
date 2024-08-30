package com.example.viewmodeldice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DiceViewModel : ViewModel() {

    /**
     * _uiState is private since we only want to modify DiceUiState inside the ViewModel
     */
    private val _uiState = MutableStateFlow(DiceUIState())

    /**
     * uiState is public since it can't be modified because it can be accessed outside the viewmodel
     */
    val uiState: StateFlow<DiceUIState> = _uiState.asStateFlow()

    /**
     * List of dices drawables
     */
    private var dicesList: List<Int> = listOf(
        R.drawable.dice_one,
        R.drawable.dice_two,
        R.drawable.dice_three,
        R.drawable.dice_four,
        R.drawable.dice_five,
        R.drawable.dice_six
    )

    /**
     * Funciton to update _uiState
     */
    fun rollDice() {
        // Using update we can access _uiState.value and define to it a new value
//        _uiState.update { currentDiceUiState ->
//            val diceOne = obtainDice()
//            val diceTwo = obtainDice()
//            _uiState.value.copy(
//                diceOne = diceOne,
//                diceTwo = diceTwo,
//                numberOfRolls = currentDiceUiState.numberOfRolls + 1,
//                dicesAreEqual = diceOne == diceTwo
//            )
//        }

        // IMPORTANT: Only use one solution (up) on another (down)

        // This is another way to do it
        val diceOne = obtainDice()
        val diceTwo = obtainDice()
        _uiState.value = _uiState.value.copy(
            diceOne = diceOne,
            diceTwo = diceTwo,
            numberOfRolls = _uiState.value.numberOfRolls + 1,
            dicesAreEqual = diceOne == diceTwo
        )
    }

    /**
     * Obtain a random value from the dicesList
     */
    private fun obtainDice() : Int =
        dicesList.random()

}