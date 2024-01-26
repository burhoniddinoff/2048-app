package com.example.a2048app.domain

import android.widget.Toast
import kotlin.random.Random

class AppRepositoryImpl : AppRepository {

    companion object {
        private lateinit var instance: AppRepository

        fun getInstance(): AppRepository {
            if (!(Companion::instance.isInitialized)) instance = AppRepositoryImpl()
            return instance
        }
    }

    private var matrix = arrayOf(
        arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0)
    )

    private val addElement = 2

    init {
        addNewElement()
        addNewElement()
    }

    override fun getMatrix(): Array<Array<Int>> = matrix

    private fun addNewElement() {
        val empty = ArrayList<Pair<Int, Int>>()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) empty.add(Pair(i, j))
            }
        }

        if (empty.isEmpty()) return

        val randomIndex = Random.nextInt(0, empty.size)
        val findPairByRandomIndex = empty[randomIndex]
        matrix[findPairByRandomIndex.first][findPairByRandomIndex.second] = addElement
    }

    private fun createBasicMatrix() = arrayOf(
        arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0)
    )


    override fun moveUp() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean


        for (i in matrix.indices) {
            index = 0
            isAdded = false

            for (j in matrix[i].indices) {
                if (matrix[j][i] == 0) continue
                if (newMatrix[0][i] == 0) {
                    newMatrix[0][i] = matrix[j][i]
                    continue
                }

                if (newMatrix[index][i] == matrix[j][i] && !isAdded) {
                    newMatrix[index][i] *= 2
                    isAdded = true
                } else {
                    newMatrix[++index][i] = matrix[j][i]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addNewElement()
        finish()
    }

    override fun moveRight() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean


        for (i in matrix.indices) {
            index = 3
            isAdded = false

            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[i][3] == 0) {
                    newMatrix[i][3] = matrix[i][j]
                    continue
                }

                if (newMatrix[i][index] == matrix[i][j] && !isAdded) {
                    newMatrix[i][index] *= 2
                    isAdded = true
                } else {
                    newMatrix[i][--index] = matrix[i][j]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addNewElement()
        finish()
    }

    override fun moveDown() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean


        for (i in matrix.indices) {
            index = 3
            isAdded = false

            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[j][i] == 0) continue
                if (newMatrix[3][i] == 0) {
                    newMatrix[3][i] = matrix[j][i]
                    continue
                }

                if (newMatrix[index][i] == matrix[j][i] && !isAdded) {
                    newMatrix[index][i] *= 2
                    isAdded = true
                } else {
                    newMatrix[--index][i] = matrix[j][i]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addNewElement()
        finish()
    }

    override fun moveLeft() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean


        for (i in matrix.indices) {
            index = 0
            isAdded = false

            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[i][0] == 0) {
                    newMatrix[i][0] = matrix[i][j]
                    continue
                }

                if (newMatrix[i][index] == matrix[i][j] && !isAdded) {
                    newMatrix[i][index] *= 2
                    isAdded = true
                } else {
                    newMatrix[i][++index] = matrix[i][j]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addNewElement()
        finish()
    }

    override fun finish(): Boolean {

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) return false
            }
        }


        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (j < matrix[i].size - 1 && matrix[i][j] == matrix[i][j + 1]) return false
                if (i < matrix.size - 1 && matrix[i][j] == matrix[i + 1][j]) return false
            }
        }

        return true
    }

    override fun resetGame() {
        matrix = createBasicMatrix()
        addNewElement()
        addNewElement()
    }

}