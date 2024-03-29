package com.example.a2048app.domain

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.example.a2048app.utils.deepCopyCopyMatrix
import kotlin.random.Random

class AppRepositoryImpl(private val context: Context) : AppRepository {

    companion object {
        private const val PREFS_NAME = "2048Prefs"
        private const val MATRIX_KEY = "matrixKey"
        private const val SCORE_KEY = "scoreKey"
        private const val BEST_SCORE_KEY = "bestScoreKey"

        private lateinit var instance: AppRepository

        fun getInstance(context: Context): AppRepository {
            if (!(Companion::instance.isInitialized)) {
                instance = AppRepositoryImpl(context)
                (instance as AppRepositoryImpl).loadGameData()
            }
            return instance
        }

        fun getRepository(): AppRepository = instance
    }

    private var matrix = arrayOf(
        arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0)
//        arrayOf(2, 4, 8, 16), arrayOf(32, 64, 128, 256), arrayOf(512, 1024, 2, 4), arrayOf(8, 16, 0, 0)
    )

    private var score: Int = 0
    private var bestScore: Int = 0
    private val addElement = 2
    private var lastStepMatrix: Array<Array<Int>> = matrix.deepCopyCopyMatrix()
    private var lastStepScore = 0

    init {
        addNewElement()
    }

    override fun getMatrix(): Array<Array<Int>> = matrix
    override fun getScore(): Int = score
    override fun getBestScore(): Int {
        if (score >= bestScore) bestScore = score
        return bestScore
    }

    private fun saveGameData() {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val matrixString = matrix.flatten().joinToString(",")
        editor.putString(MATRIX_KEY, matrixString)

        editor.putInt(SCORE_KEY, score)
        editor.putInt(BEST_SCORE_KEY, bestScore)

        editor.apply()
    }

    private fun loadGameData() {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val matrixString = prefs.getString(MATRIX_KEY, null)
        matrix = if (matrixString != null) {
            val values = matrixString.split(",").map { it.toInt() }
            Array(4) { i -> Array(4) { j -> values[i * 4 + j] } }
        } else createBasicMatrix()

        score = prefs.getInt(SCORE_KEY, 0)
        bestScore = prefs.getInt(BEST_SCORE_KEY, 0)
        lastStepMatrix = matrix.deepCopyCopyMatrix()
    }

    private fun addNewElement() {
        val empty = ArrayList<Pair<Int, Int>>()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) if (matrix[i][j] == 0) empty.add(Pair(i, j))
        }

        if (empty.isEmpty()) return

        val randomIndex = Random.nextInt(0, empty.size)
        val findPairByRandomIndex = empty[randomIndex]
        matrix[findPairByRandomIndex.first][findPairByRandomIndex.second] = addElement
    }

    private fun createBasicMatrix() = arrayOf(
        arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0)
//        arrayOf(2, 4, 8, 16), arrayOf(32, 64, 128, 256), arrayOf(512, 1024, 2, 4), arrayOf(8, 16, 123, 1230)
    )

    private fun animateTileMovement(tileView: AppCompatTextView, row: Int, col: Int) {
        val translationX = col * tileView.width.toFloat()
        val translationY = row * tileView.height.toFloat()

        tileView.animate().translationX(translationX).translationY(translationY)
            .setDuration(200) // Adjust the duration as needed
            .start()
    }

    override fun moveUp() {
        lastStepMatrix = matrix.deepCopyCopyMatrix()
        lastStepScore = score


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
                    score += newMatrix[index][i]
                    isAdded = true
                } else {
                    newMatrix[++index][i] = matrix[j][i]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addNewElement()

        if (isScoreUpdated()) saveGameData()

        finish()
    }

    override fun moveRight() {
        lastStepMatrix = matrix.deepCopyCopyMatrix()
        lastStepScore = score

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
                    score += newMatrix[i][index]
                    isAdded = true
                } else {
                    newMatrix[i][--index] = matrix[i][j]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addNewElement()

        if (isScoreUpdated()) saveGameData()

        finish()
    }

    override fun moveDown() {
        lastStepMatrix = matrix.deepCopyCopyMatrix()
        lastStepScore = score

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
                    score += newMatrix[index][i]
                    isAdded = true
                } else {
                    newMatrix[--index][i] = matrix[j][i]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addNewElement()

        if (isScoreUpdated()) saveGameData()

        finish()
    }

    override fun moveLeft() {
        lastStepMatrix = matrix.deepCopyCopyMatrix()
        lastStepScore = score

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
                    score += newMatrix[i][index]
                    isAdded = true
                } else {
                    newMatrix[i][++index] = matrix[i][j]
                    isAdded = false
                }
            }
        }

        matrix = newMatrix
        addNewElement()

        if (isScoreUpdated()) saveGameData()

        finish()
    }

    private fun isScoreUpdated(): Boolean {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (j < matrix[i].size - 1 && matrix[i][j] == matrix[i][j + 1]) return true
                if (i < matrix.size - 1 && matrix[i][j] == matrix[i + 1][j]) return true
            }
        }

        return false
    }

    override fun finish(): Boolean {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) if (matrix[i][j] == 0) return false
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
        score = 0
        matrix = createBasicMatrix()
        addNewElement()
        addNewElement()
    }

    override fun getLastStepMatrix() {
        matrix = lastStepMatrix.deepCopyCopyMatrix()
        score = lastStepScore
    }
}