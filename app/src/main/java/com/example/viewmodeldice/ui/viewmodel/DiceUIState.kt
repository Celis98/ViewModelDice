package com.example.viewmodeldice.ui.viewmodel

import androidx.annotation.DrawableRes
import com.example.viewmodeldice.R
import com.example.viewmodeldice.data.entities.ResultEntity

data class DiceUIState(
    @DrawableRes
    val diceOne: Int? = null,
    @DrawableRes
    val diceTwo: Int = R.drawable.dice_one,
    val numberOfRolls: Int = 0,
    val dicesAreEqual: Boolean? = null,
    val historyResults: List<ResultEntity> = emptyList()
)
