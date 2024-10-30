package com.example.viewmodeldice.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.viewmodeldice.databinding.ActivityNavigationMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}