package com.koniukhov.memorygame

import android.os.Handler
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.koniukhov.memorygame.data.Card
import com.koniukhov.memorygame.viewmodels.GameViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock


const val NUM_ROW = 3
const val NUM_COL = 2
@ExperimentalCoroutinesApi
class CardTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Mock
    var mockHandler: Handler = mock(Handler::class.java)

    private val iconsIds = listOf(10, 20, 33, 55, 980, 1020, 999, 318)
    private val gameViewModel = GameViewModel(iconsIds)
    private var cards: List<Card>? = emptyList()
    private var sortedCards: List<Card>? = emptyList<Card>()

    private val expectedValues = listOf(2, 2, 2)

    @Before
    fun setUp() {
        gameViewModel.setNumCol(NUM_COL)
        gameViewModel.setNumRow(NUM_ROW)

        gameViewModel.initCards()
        cards = gameViewModel.cards.value
        sortedCards = cards?.sortedBy { it.resId }
    }

    @Test
    fun testCardsAreUnique(){
        val gridSize = NUM_ROW * NUM_COL

        val uniqueMap = cards?.groupBy { it.resId }
        val uniqueSizes = uniqueMap?.values?.map { it.size }

        assertEquals(gridSize, cards?.size)
        assertEquals(expectedValues,uniqueSizes)
    }

    @Test
    fun clickOnBlockThenCardMustBeFlipped(){
        val firstCard = cards?.get(0)

        gameViewModel.selectCard(firstCard!!, mockHandler)

        assertTrue(gameViewModel.cards.value?.get(0)?.isFlipped!!)
    }

    @Test
    fun clickOnTwoEqualBlocksThenMustBeFlipped(){
        val sortedCards = cards?.sortedBy { it.resId }
        val firstCard = sortedCards?.get(0)
        val secondCard = sortedCards?.get(1)

        gameViewModel.selectCard(firstCard!!, mockHandler)
        gameViewModel.selectCard(secondCard!!, mockHandler)

        assertTrue(firstCard.isFlipped)
        assertTrue(secondCard.isFlipped)

    }

    @Test
    fun clickOnTwoEqualBlocksThenMustBeMatched(){
        val sortedCards = cards?.sortedBy { it.resId }
        val firstCard = sortedCards?.get(0)
        val secondCard = sortedCards?.get(1)

        gameViewModel.selectCard(firstCard!!, mockHandler)
        gameViewModel.selectCard(secondCard!!, mockHandler)

        assertTrue(firstCard.isMatched)
        assertTrue(secondCard.isMatched)

    }

    @Test
    fun gameMustBeOver(){
        val sortedCards = cards?.sortedBy { it.resId }

        gameViewModel.selectCard(sortedCards?.get(0)!!, mockHandler)
        gameViewModel.selectCard(sortedCards[1], mockHandler)

        gameViewModel.selectCard(sortedCards[2], mockHandler)
        gameViewModel.selectCard(sortedCards[3], mockHandler)

        gameViewModel.selectCard(sortedCards[4], mockHandler)
        gameViewModel.selectCard(sortedCards[5], mockHandler)


        assertTrue(gameViewModel.isGameOver())
    }
}