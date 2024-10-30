package com.example.viewmodeldice.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val diceOne: Int,
    val diceTwo: Int
)
