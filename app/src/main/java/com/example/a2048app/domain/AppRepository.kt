package com.example.a2048app.domain

interface AppRepository {

    fun moveUp()
    fun moveRight()
    fun moveDown()
    fun moveLeft()

    fun getMatrix(): Array<Array<Int>>
    fun resetGame()
    fun finish(): Boolean
    fun getScore(): Int
    fun getBestScore(): Int
}