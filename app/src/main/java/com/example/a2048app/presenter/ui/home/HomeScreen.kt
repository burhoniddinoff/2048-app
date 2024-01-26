package com.example.a2048app.presenter.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.a2048app.R
import com.example.a2048app.databinding.ScreenHomeBinding

class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        openNewGame()
        openResumeGame()
        openInfo()
    }

    private fun openNewGame() {
        binding.btnNewGame.setOnClickListener {
            navController.navigate(HomeScreenDirections.actionHomeScreenToGameScreen())
        }
    }

    private fun openResumeGame() {
        binding.btnKeepGoing.setOnClickListener {
            navController.navigate(HomeScreenDirections.actionHomeScreenToGameScreen())
        }
    }


    private fun openInfo() {
        binding.btnHowToPlay.setOnClickListener {
            navController.navigate(HomeScreenDirections.actionHomeScreenToInfoScreen())
        }
    }

}