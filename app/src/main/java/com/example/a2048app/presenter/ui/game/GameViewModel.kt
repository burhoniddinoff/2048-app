package com.example.a2048app.presenter.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2048app.domain.AppRepository
import com.example.a2048app.domain.AppRepositoryImpl

class GameViewModel : ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl.getRepository()

    private val _matrixLiveData = MutableLiveData<Array<Array<Int>>>()
    val matrixLiveData: LiveData<Array<Array<Int>>> get() = _matrixLiveData


    private val _scoreLiveData = MutableLiveData<Int>()
    val scoreLiveData: LiveData<Int> get() = _scoreLiveData

    private val _bestScoreLiveData = MutableLiveData<Int>()
    val bestScoreLiveData: LiveData<Int> get() = _bestScoreLiveData

    fun loadData() {
        _matrixLiveData.value = repository.getMatrix()
        _scoreLiveData.value = repository.getScore()
        _bestScoreLiveData.value = repository.getBestScore()
    }

    fun moveUp() {
        repository.moveUp()
        loadData()
    }

    fun moveRight() {
        repository.moveRight()
        loadData()
    }

    fun moveDown() {
        repository.moveDown()
        loadData()
    }

    fun moveLeft() {
        repository.moveLeft()
        loadData()
    }

    fun restartGame() {
        repository.resetGame()
        loadData()
    }

    fun finish(): Boolean {
        return repository.finish()
    }

}