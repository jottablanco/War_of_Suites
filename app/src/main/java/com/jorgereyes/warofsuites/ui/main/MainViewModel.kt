package com.jorgereyes.warofsuites.ui.main

import androidx.lifecycle.ViewModel
import com.jorgereyes.warofsuites.data.model.*
import com.jorgereyes.warofsuites.data.repository.CardBuilder

class MainViewModel : ViewModel() {

  private val cardBuilder = CardBuilder()
  private var deck: Deck = Deck(cardBuilder)
  var suitesValueMap : Map<Int, SuiteName> = mutableMapOf()
  private var player1Score: MutableList<Card> = mutableListOf()
  private var player2Score: MutableList<Card> = mutableListOf()
  private var player1Deck: MutableList<Card> = mutableListOf()
  private var player2Deck: MutableList<Card> = mutableListOf()

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

  fun createPlayersDecks() {
    val subLists = deck.getMainDeck().chunked(26)
    player1Deck.addAll(subLists[0])
    player2Deck.addAll(subLists[1])
  }

  fun dealCard(player: Player): Map<Player, Card> {
    val player1Card: Card
    val player2Card: Card
    var cardsMap = mutableMapOf<Player, Card>()
    when (player) {
      Player.PLAYER_1 -> {
        player1Card = player1Deck.first()
        player1Deck.remove(player1Card)
        cardsMap[player] = player1Card
      }
      Player.PLAYER_2 -> {
        player2Card = player2Deck.first()
        player2Deck.remove(player2Card)
        cardsMap[player] = player2Card
      }
    }
    return cardsMap
  }

  fun addToPlayersDiscardsPile(player1Card: Card, player2Card: Card, player: Player) {
    when (player) {
      Player.PLAYER_1 -> {
        player1Score.addAll(listOf(player1Card, player2Card))
      }
      Player.PLAYER_2 -> {
        player2Score.addAll(listOf(player1Card, player2Card))
      }
    }
  }


  fun getPlayersRoundScore(player: Player): Int {
    return when (player) {
      Player.PLAYER_1 -> player1Score.size
      Player.PLAYER_2 -> player2Score.size
    }
  }

  fun checkForGameOver(): Boolean {
    return player1Deck.size == 0 && player2Deck.size == 0
  }

  fun getWinner(): Player {
    return if (player1Score.size > player2Score.size) {
      Player.PLAYER_1
    } else {
      Player.PLAYER_2
    }
  }

  fun restartGame() {
    player1Score.clear()
    player2Score.clear()
    player1Deck.clear()
    player2Deck.clear()
    deck = Deck(cardBuilder)
    deck.shuffleDeck()
    createPlayersDecks()
  }

  fun getPlayerDeckSize(player: Player): Int {
    return when (player) {
      Player.PLAYER_1 -> player1Deck.size
      Player.PLAYER_2 -> player2Deck.size
    }
  }

}
