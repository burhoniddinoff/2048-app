package com.example.a2048app.utils
fun Array<Array<Int>>.deepCopyCopyMatrix(): Array<Array<Int>> {
    val newMatrix = Array(4) { Array(4) { 0 } }

    for (i in this.indices) {
        for (j in this[i].indices) {
            newMatrix[i][j] = this[i][j]
        }
    }

    return newMatrix
}