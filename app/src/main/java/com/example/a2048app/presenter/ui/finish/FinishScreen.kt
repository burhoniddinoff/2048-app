package com.example.a2048app.presenter.ui.finish

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.a2048app.R
import com.example.a2048app.databinding.ScreenFinishBinding
import com.example.a2048app.presenter.ui.game.GameViewModel

class FinishScreen : Fragment(R.layout.screen_finish) {

    private val binding by viewBinding(ScreenFinishBinding::bind)
    private val navArgs: FinishScreenArgs by navArgs()
    private val viewModel: GameViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnScore.text = "Score\n ${navArgs.score}"

        binding.btnShare.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://t.me/")
                )
            )
        }

        binding.btnTryAgain.setOnClickListener {
            viewModel.restartGame()
            findNavController().popBackStack()
        }

    }

}