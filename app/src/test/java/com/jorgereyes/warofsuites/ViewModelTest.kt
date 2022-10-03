package com.jorgereyes.warofsuites

import android.app.Application
import com.jorgereyes.warofsuites.data.model.*
import com.jorgereyes.warofsuites.domain.usecase.GetScoresUseCase
import com.jorgereyes.warofsuites.domain.usecase.SaveWinnerUseCase
import com.jorgereyes.warofsuites.presentation.viewModel.MainViewModel
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*


class ViewModelTest {

  private val getScoresUseCase = mockk<GetScoresUseCase>()
  private val saveWinnerUseCase = mockk<SaveWinnerUseCase>()
  private val application = mockk<Application>()
  private val player1 = Player(UUID.randomUUID(), PlayerName.PLAYER_1, 10)
  private val player2 = Player(UUID.randomUUID(), PlayerName.PLAYER_2, 0)

  private lateinit var viewModel: MainViewModel
  private lateinit var deck: Deck

  @Before
  fun setUp() {
    viewModel = MainViewModel(
      application,
      getScoresUseCase,
      saveWinnerUseCase
    )
  }


  @Test
  fun test_ShuffleDeck() {
    deck = viewModel.getInGameDeck()
    var firstCardNameBeforeShuffle = ""
    var firstCardAfterShuffle = ""

    firstCardNameBeforeShuffle = deck.getMainDeck().first().cardName
    viewModel.shuffleMainDeck()
    firstCardAfterShuffle = viewModel.getInGameDeck().getMainDeck().first().cardName

    assertNotEquals("Deck has been shuffled", firstCardNameBeforeShuffle, firstCardAfterShuffle)
  }

  @Test
  fun test_splitMainDeckEqually() {
    deck = viewModel.getInGameDeck()
    viewModel.createPlayersDecks()

    val player1Deck = viewModel.getPlayerDeckSize(player1)
    val player2Deck = viewModel.getPlayerDeckSize(player2)

    assertEquals("Player's decks are the same size", player1Deck, player2Deck)
  }

  @Test
  fun test_defineHigherSuiteValue() {
    val values = viewModel.defineSuiteValue()
    val highest = values[HIGHEST_SUITE_VALUE]
    val lowest = values[LOWEST_SUITE_VALUE]

    assertNotNull(highest)
    assertNotNull(lowest)
  }

  @Test
  fun test_GetRoundWinner() {
    viewModel.shuffleMainDeck()
    viewModel.createPlayersDecks()

    val suitesValues = viewModel.defineSuiteValue()
    val player1Card = viewModel.dealCard(player1)[player1]
    val player2Card = viewModel.dealCard(player2)[player2]

    val winner = viewModel.getRoundWinner(player1Card!!, player2Card!!, suitesValues)

    assertNotNull("Round winner defined", winner)
  }

  @Test
  fun test_getPlayersDeckSizeAfterDealCards() {
    viewModel.shuffleMainDeck()
    viewModel.createPlayersDecks()

    var player1DeckBeforeDeal = 0
    var player2DeckBeforeDeal = 0
    var player1DeckAfterDeal = 0
    var player2DeckAfterDeal = 0


    player1DeckBeforeDeal = viewModel.getPlayerDeckSize(player1)
    player2DeckBeforeDeal = viewModel.getPlayerDeckSize(player2)

    viewModel.dealCard(player1)
    viewModel.dealCard(player2)

    player1DeckAfterDeal = viewModel.getPlayerDeckSize(player1)
    player2DeckAfterDeal = viewModel.getPlayerDeckSize(player2)

    assertNotEquals("player1 deck is not the same size", player1DeckBeforeDeal, player1DeckAfterDeal)
    assertNotEquals("player2 deck is not the same size", player2DeckBeforeDeal, player2DeckAfterDeal)
  }

  @Test
  fun test_discardPiles() {
    viewModel.shuffleMainDeck()
    viewModel.createPlayersDecks()
    val suitesValues = viewModel.defineSuiteValue()
    val player1Card = Card(14, "ACE of SPADES", Suite(SuiteName.SPADES), 1)
    val player2Card = Card(2, "TWO of HEARTS", Suite(SuiteName.HEARTS), 1)

    val winnerScoreBeforePlay = viewModel.getPlayersRoundScore(player1)

    val winner = viewModel.getRoundWinner(player1Card, player2Card, suitesValues)

    val winnerPlayer = if (winner.name == player1.playerTag.name) {
      player1
    } else {
      player2
    }

    viewModel.addToPlayersDiscardsPile(player1Card, player2Card, winnerPlayer)

    val winnerScoreAfterPlay = viewModel.getPlayersRoundScore(winnerPlayer)

    assertTrue("Player 1 discardPile has increased by two cards", winnerScoreBeforePlay != winnerScoreAfterPlay)
  }

  @Test
  fun test_getGameWinner() {
    viewModel.shuffleMainDeck()
    viewModel.createPlayersDecks()
    val suitesValues = viewModel.defineSuiteValue()
    val player1Card = Card(14, "ACE of SPADES", Suite(SuiteName.SPADES), 1)
    val player2Card = Card(2, "TWO of HEARTS", Suite(SuiteName.HEARTS), 1)


    val roundWinner = viewModel.getRoundWinner(player1Card, player2Card, suitesValues)

    val winnerPlayer = if (roundWinner.name == player1.playerTag.name) {
      player1
    } else {
      player2
    }

    viewModel.addToPlayersDiscardsPile(player1Card, player2Card, winnerPlayer)

    val gameWinner = viewModel.getWinner()

    assertTrue(roundWinner == gameWinner)

  }


  companion object {
    private const val HIGHEST_SUITE_VALUE = 4
    private const val LOWEST_SUITE_VALUE = 1
  }
}
