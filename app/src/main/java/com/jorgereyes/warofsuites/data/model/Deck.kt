package com.jorgereyes.warofsuites.data.model

import com.jorgereyes.warofsuites.data.repository.CardBuilder

class Deck(cardBuilder: CardBuilder) {
  private var deck: MutableList<Card> = mutableListOf()
  private val builder: CardBuilder = cardBuilder
  private var player1Deck: MutableList<Card> = mutableListOf()
  private var player2Deck: MutableList<Card> = mutableListOf()

  init {
    createDeck()
  }

  private fun createDeck() {
    deck = builder.createCards()
  }

  fun shuffleDeck() {
    deck = deck.shuffled() as MutableList<Card>
  }

  fun getMainDeck() : MutableList<Card> {
    return deck
  }


  fun getPlayer1Deck(): MutableList<Card> {
    return player1Deck
  }

  fun getPlayer2Deck(): MutableList<Card> {
    return player2Deck
  }
}
