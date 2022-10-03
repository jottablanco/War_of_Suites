package com.jorgereyes.warofsuites.presentation.viewModel

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.jorgereyes.warofsuites.data.dataUtils.CardBuilder
import com.jorgereyes.warofsuites.data.model.*
import com.jorgereyes.warofsuites.domain.usecase.GetScoresUseCase
import com.jorgereyes.warofsuites.domain.usecase.SaveWinnerUseCase
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel constructor(
  application: Application,
  private val getScoresUseCase: GetScoresUseCase,
  private val saveWinnerUseCase: SaveWinnerUseCase
) :
  AndroidViewModel(application) {

  private val cardBuilder = CardBuilder()
  private var deck: Deck = Deck(cardBuilder)
  private var player1Score: MutableList<Card> = mutableListOf()
  private var player2Score: MutableList<Card> = mutableListOf()
  private var player1Deck: MutableList<Card> = mutableListOf()
  private var player2Deck: MutableList<Card> = mutableListOf()

  var suitesValueMap: Map<Int, SuiteName> = mutableMapOf()

  lateinit var player1 : Player
  lateinit var player2: Player

  fun defineSuiteValue(): MutableMap<Int, SuiteName> {
    var suiteValues = mutableMapOf<Int, SuiteName>()
    val suites = SuiteName.values()
    val suiteRandomValues = mutableListOf<Int>()

    suiteRandomValues.addAll(listOf(1, 2, 3, 4))
    suiteRandomValues.shuffle()
    suites.shuffle()

    for (suiteName in suites) {

      val value = suiteRandomValues.first()
      suiteValues[value] = suiteName
      suiteRandomValues.remove(value)

    }

    return suiteValues
  }


  fun shuffleMainDeck() {
    deck.shuffleDeck()
  }

  @VisibleForTesting
  fun getInGameDeck() : Deck {
    return deck
  }

  fun createPlayers() {
    player1 = Player(UUID.randomUUID(), PlayerName.PLAYER_1, 0)
    player2 = Player(UUID.randomUUID(), PlayerName.PLAYER_2, 0)
  }

  fun createPlayersDecks() {
    player1Deck.clear()
    player2Deck.clear()
    val subLists = deck.getMainDeck().chunked(26)
    player1Deck.addAll(subLists[0])
    player2Deck.addAll(subLists[1])
  }

  fun dealCard(player: Player): Map<Player, Card> {
    val player1Card: Card
    val player2Card: Card
    var cardsMap = mutableMapOf<Player, Card>()
    when (player.playerTag) {
      PlayerName.PLAYER_1 -> {
        player1Card = player1Deck.first()
        player1Deck.remove(player1Card)
        cardsMap[player] = player1Card
      }
      PlayerName.PLAYER_2 -> {
        player2Card = player2Deck.first()
        player2Deck.remove(player2Card)
        cardsMap[player] = player2Card
      }
    }
    return cardsMap
  }

  fun getRoundWinner(player1Card: Card, player2Card: Card, suitesValues: Map<Int, SuiteName>) : PlayerName {
    return when {
      player1Card.cardValue > player2Card.cardValue -> PlayerName.PLAYER_1
      player1Card.cardValue < player2Card.cardValue -> PlayerName.PLAYER_2
      else -> {
        val keyPlayer1 = suitesValues.filterValues { it == player1Card.suit.suiteName }.keys.first()
        val keyPlayer2 = suitesValues.filterValues { it == player2Card.suit.suiteName }.keys.first()

        if (keyPlayer1 > keyPlayer2) {
          PlayerName.PLAYER_1
        } else {
          PlayerName.PLAYER_2
        }
      }
    }
  }

  fun addToPlayersDiscardsPile(player1Card: Card, player2Card: Card, player: Player) {
    when (player.playerTag) {
      PlayerName.PLAYER_1 -> {
        player1Score.addAll(listOf(player1Card, player2Card))
      }
      PlayerName.PLAYER_2 -> {
        player2Score.addAll(listOf(player1Card, player2Card))
      }
    }
  }


  fun getPlayersRoundScore(player: Player): Int {
    return when (player.playerTag) {
      PlayerName.PLAYER_1 -> player1Score.size
      PlayerName.PLAYER_2 -> player2Score.size
    }
  }

  fun checkForGameOver(): Boolean {
    return player1Deck.size == 0 && player2Deck.size == 0
  }

  fun getWinner(): PlayerName {
    return if (player1Score.size > player2Score.size) {
      PlayerName.PLAYER_1
    } else {
      PlayerName.PLAYER_2
    }
  }

  fun restartGame() {
    player1Score.clear()
    player2Score.clear()
    player1Deck.clear()
    player2Deck.clear()
    createPlayers()
    deck = Deck(cardBuilder)
    deck.shuffleDeck()
    createPlayersDecks()
  }

  fun getPlayerDeckSize(player: Player): Int {
    return when (player.playerTag) {
      PlayerName.PLAYER_1 -> player1Deck.size
      PlayerName.PLAYER_2 -> player2Deck.size
    }
  }

  fun saveGameWinner(player: Player) = viewModelScope.launch {
    saveWinnerUseCase.execute(player)
  }

  fun getScoresHistory() = liveData {
    getScoresUseCase.execute().collect {
      emit(it)
    }
  }

}
