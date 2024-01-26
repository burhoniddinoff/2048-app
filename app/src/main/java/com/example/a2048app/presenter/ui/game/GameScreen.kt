package com.example.a2048app.presenter.ui.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.a2048app.R
import com.example.a2048app.data.enum1.SideEnum
import com.example.a2048app.databinding.ScreenGameBinding
import com.example.a2048app.domain.AppRepository
import com.example.a2048app.domain.AppRepositoryImpl
import com.example.a2048app.utils.MyBackgroundUtil
import com.example.a2048app.utils.MyTouchListener

class GameScreen : Fragment(R.layout.screen_game) {

    private val binding by viewBinding(ScreenGameBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val views = ArrayList<AppCompatTextView>(16)
    private val viewModel: GameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupViews()
        setupTouchListener()
        setupObservers()

        binding.menu.setOnClickListener {
            navController.popBackStack()
        }

        binding.restart.setOnClickListener {
            viewModel.restartGame()
        }

        viewModel.gameFinishLiveData.observe(viewLifecycleOwner) { isGameFinished ->
            if (isGameFinished) {
                navController.navigate(GameScreenDirections.actionGameScreenToInfoScreen())
            }
        }

        viewModel.scoreLiveData.observe(viewLifecycleOwner) { score ->
            updateScore(score)
        }

    }

    private fun updateScore(score: Int) {
        binding.textScore.text = score.toString()
    }

    private fun setupViews() {
        for (i in 0 until binding.mainView.childCount) {
            val line = binding.mainView[i] as LinearLayoutCompat
            for (j in 0 until line.childCount) {
                views.add(line[j] as AppCompatTextView)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupTouchListener() {
        val myTouchListener = MyTouchListener(requireContext())
        myTouchListener.setActionSideEnumListener {
            when (it) {
                SideEnum.UP -> viewModel.moveUp()
                SideEnum.RIGHT -> viewModel.moveRight()
                SideEnum.DOWN -> viewModel.moveDown()
                SideEnum.LEFT -> viewModel.moveLeft()
            }
        }
        binding.mainView.setOnTouchListener(myTouchListener)
    }

    private fun setupObservers() {
        viewModel.matrixLiveData.observe(viewLifecycleOwner) { matrix ->
            updateUI(matrix)
        }
    }

    private fun updateUI(matrix: Array<Array<Int>>) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) views[i * 4 + j].text = ""
                else views[i * 4 + j].text = "${matrix[i][j]}"

                views[i * 4 + j].setBackgroundResource(MyBackgroundUtil.backgroundByAmount(matrix[i][j]))
            }
        }
    }

}