package com.example.a2048app.presenter.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.a2048app.R
import com.example.a2048app.databinding.ScreenHomeBinding
import com.example.a2048app.presenter.ui.game.GameViewModel

class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: GameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        openNewGame()
        openResumeGame()
        openInfo()
        openRate()
    }

    private fun openNewGame() {
        binding.btnNewGame.setOnClickListener {
            viewModel.restartGame()
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

    private fun openRate() {
        binding.btnRate.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${requireContext().packageName}")))
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${requireContext().packageName}")))
            }
        }
    }

}