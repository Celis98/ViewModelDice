package com.example.viewmodeldice

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.viewmodeldice.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewmodel: DiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUIStateLifecycle()
        initListeners()
    }

    private fun initListeners() {
        binding.btnRollDice.setOnClickListener {
            viewmodel.rollDice()
        }
    }

    private fun initUIStateLifecycle() {
        // Once the activity has a lifecycle the code inside launch is executed
        lifecycleScope.launch {
            // Collect uiState value everytime it is changed
            viewmodel.uiState.collect { uiState ->
                with(binding) {
                    // Validate that diceOne is not null
                    uiState.diceOne?.let {
                        // Set drawable to ivDiceOne
                        ivDiceOne.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                uiState.diceOne
                            )
                        )
                    }
                    // Set drawable to ivDiceTwo
                    ivDiceTwo.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MainActivity,
                            uiState.diceTwo
                        )
                    )
                    // Set text to tvRolls
                    tvRolls.text = getString(R.string.rolls_attemps, uiState.numberOfRolls)
                    // Validate that dicesAreEqual is not null
                    uiState.dicesAreEqual?.let {
                        // Using apply, we access to the internal variables inside ivDicesAreEqual
                        ivDicesAreEqual.apply {
                            setImageDrawable(
                                ContextCompat.getDrawable(
                                    this@MainActivity,
                                    if (uiState.dicesAreEqual == true) R.drawable.ic_check else R.drawable.ic_close
                                )
                            )
                            setColorFilter(
                                ContextCompat.getColor(
                                    this@MainActivity,
                                    if (uiState.dicesAreEqual == true) R.color.green else R.color.red
                                )
                            )
                        }
                        // The previous code (the ivDicesAreEqual.apply { ... } is the same as the below code
                        // Don't uncomment this code, is just an example
//                        ivDicesAreEqual.setImageDrawable(
//                            ContextCompat.getDrawable(
//                                this@MainActivity,
//                                if (uiState.dicesAreEqual == true) R.drawable.ic_check else R.drawable.ic_close
//                            )
//                        )
//                        ivDicesAreEqual.setColorFilter(
//                            ContextCompat.getColor(
//                                this@MainActivity,
//                                if (uiState.dicesAreEqual == true) R.color.green else R.color.red
//                            )
//                        )
                    }
                }
            }
        }
    }
}