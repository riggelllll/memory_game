package com.koniukhov.memorygame.viewmodels

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    var numRow = 0
        private set
    var numCol = 0
        private set

    fun setNumRow(numRow: Int){
        this.numRow = numRow
    }

    fun setNumCol(numCol: Int){
        this.numCol = numCol
    }
}