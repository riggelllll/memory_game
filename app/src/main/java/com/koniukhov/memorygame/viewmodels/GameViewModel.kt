package com.koniukhov.memorygame.viewmodels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.koniukhov.memorygame.data.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

private const val DELAY = 1000L

@HiltViewModel
class GameViewModel @Inject constructor(private val iconsIds: List<Int>) : ViewModel() {
    var numRow = 0
        private set
    var numCol = 0
        private set

    private var uniqueCards: MutableSet<Int> = mutableSetOf()

    var cards =  MutableLiveData<List<Card>>()
        private set

    private val pairsFound = MutableLiveData(0)

    fun setNumRow(numRow: Int){
        this.numRow = numRow
    }

    fun setNumCol(numCol: Int){
        this.numCol = numCol
    }

    private fun setUniqueCards(){
        val gridSize = numRow * numCol
        val uniqueCardsNum = gridSize / 2

        do {
            val rNum = Random.nextInt(0, iconsIds.size)
            uniqueCards.add(iconsIds[rNum])
        }while (uniqueCards.size != uniqueCardsNum)
    }

    private fun setCards(){
        val cardsList = mutableListOf<Card>()

        for ( (i, r) in (0 until  numCol * numRow step 2).zip(0 until  uniqueCards.size)){
            cardsList.add(Card(i, uniqueCards.elementAt(r)))
            cardsList.add(Card(i + 1, uniqueCards.elementAt(r)))
        }
        cardsList.shuffle()
        cards.value = cardsList
    }

    fun initCards(){
        setUniqueCards()
        setCards()
    }

    fun reset() {
        pairsFound.value = 0
        uniqueCards = mutableSetOf()
    }

    fun isGameOver(): Boolean{
        return pairsFound.value == uniqueCards.size
    }

    fun selectCard(card: Card){
        if (card.isMatched || card.isFlipped) return

        val currentCards = cards.value!!.toMutableList()
        val selectedCardIndex = currentCards.indexOf(card)

        if (currentCards.none { it.isFlipped && !it.isMatched }) {
            currentCards[selectedCardIndex].flip()
        } else {
            val firstFlipped = currentCards.firstOrNull { it.isFlipped && !it.isMatched }
            if (firstFlipped != null && firstFlipped.resId == card.resId) {
                currentCards[selectedCardIndex].flip()
                currentCards[selectedCardIndex].doMatched()
                firstFlipped.doMatched()
                pairsFound.value = pairsFound.value?.plus(1)
            } else {
                currentCards[selectedCardIndex].flip()
                Handler(Looper.getMainLooper()).postDelayed({
                    currentCards[selectedCardIndex].flip()
                    firstFlipped?.flip()
                    cards.value = currentCards
                }, DELAY)
            }
        }
        cards.value = currentCards
    }
}