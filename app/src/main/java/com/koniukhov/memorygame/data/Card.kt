package com.koniukhov.memorygame.data

data class Card(val id: Int, val resId: Int, var isMatched: Boolean = false, var isFlipped: Boolean = false){
    fun flip(){
        isFlipped = !isFlipped
    }

    fun doMatched(){
        isMatched = true
    }
}
