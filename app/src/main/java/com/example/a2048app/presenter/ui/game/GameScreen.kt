package com.example.a2048app.presenter.ui.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.a2048app.R
import com.example.a2048app.data.SideEnum
import com.example.a2048app.databinding.ScreenGameBinding
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
//            viewModel.getLastStep()
            navController.popBackStack()
        }

        binding.restart.setOnClickListener {
            viewModel.restartGame()
        }

        viewModel.scoreLiveData.observe(viewLifecycleOwner) { score ->
            updateScore(score)
        }

        viewModel.bestScoreLiveData.observe(viewLifecycleOwner) { score ->
            updateBestScore(score)
        }

        viewModel.loadData()
    }


    private fun updateScore(score: Int) {
        binding.textScore.text = score.toString()
    }

    private fun updateBestScore(score: Int) {
        binding.textBest.text = score.toString()
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
                SideEnum.UP -> {
                    if (viewModel.finish()) openFinishScreen()
                    else viewModel.moveUp()
                }

                SideEnum.RIGHT -> {
                    if (viewModel.finish()) openFinishScreen()
                    else viewModel.moveRight()
                }

                SideEnum.DOWN -> {
                    if (viewModel.finish()) openFinishScreen()
                    else viewModel.moveDown()
                }

                SideEnum.LEFT -> {
                    if (viewModel.finish()) openFinishScreen()
                    else viewModel.moveLeft()
                }
            }
        }
        binding.root.setOnTouchListener(myTouchListener)
    }

    private fun openFinishScreen() {
        navController.navigate(GameScreenDirections.actionGameScreenToFinishScreen(viewModel.scoreLiveData.value.toString()))
        viewModel.restartGame()
    }

    private fun setupObservers() {
        viewModel.matrixLiveData.observe(viewLifecycleOwner) { matrix ->
            updateUI(matrix)
        }
    }

    private fun updateUI(matrix: Array<Array<Int>>) {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                val textView = views[i * 4 + j]
                if (matrix[i][j] == 0) textView.text = ""
                else textView.text = "${matrix[i][j]}"

                textView.setBackgroundResource(MyBackgroundUtil.backgroundByAmount(matrix[i][j]))
//                animateTileMovement(textView, i, j)
            }
        }
    }

    private fun animateTileMovement(tileView: AppCompatTextView, row: Int, col: Int) {
        val targetX = col * tileView.width.toFloat()
        val targetY = row * tileView.height.toFloat()

        ViewCompat.animate(tileView).x(targetX).y(targetY).setDuration(200)
            .setListener(object : ViewPropertyAnimatorListenerAdapter() {

            }).start()
    }
}