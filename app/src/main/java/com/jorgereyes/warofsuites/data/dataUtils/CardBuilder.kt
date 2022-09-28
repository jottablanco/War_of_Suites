package com.jorgereyes.warofsuites.data.dataUtils

import com.jorgereyes.warofsuites.R
import com.jorgereyes.warofsuites.data.model.Card
import com.jorgereyes.warofsuites.data.model.Suite
import com.jorgereyes.warofsuites.data.model.SuiteName

class CardBuilder {

  fun createCards(): MutableList<Card> {

    val cards = mutableListOf<Card>()

    for (suite in SuiteName.values()) {
      for (value in LOWEST_CARD_VALUE..HIGHEST_CARD_VALUE) {
        cards.add(
          Card(
            value,
            getCardName(value, suite),
            Suite(suite),
            getCardImage(value, suite)
          )
        )
      }
    }
    return cards
  }

  private fun getCardName(cardValue: Int, suiteName: SuiteName): String {
    when (cardValue) {
      14 -> return "ACE of ${suiteName.name}"
      2 -> return "TWO of ${suiteName.name}"
      3 -> return "THREE of ${suiteName.name}"
      4 -> return "FOUR of ${suiteName.name}"
      5 -> return "FIVE of ${suiteName.name}"
      6 -> return "SIX of ${suiteName.name}"
      7 -> return "SEVEN of ${suiteName.name}"
      8 -> return "EIGHT of ${suiteName.name}"
      9 -> return "NINE of ${suiteName.name}"
      10 -> return "TEN of ${suiteName.name}"
      11 -> return "JACK of ${suiteName.name}"
      12 -> return "QUEEN of ${suiteName.name}"
      13 -> return "KING of ${suiteName.name}"
      else -> {
        throw Exception("Yikes!, this should not happen")
      }
    }
  }

  private fun getCardImage(cardValue: Int, suite: SuiteName): Int {
    when (cardValue) {
      14 -> {
        return when (suite) {
          SuiteName.CLUBS -> R.mipmap.clubs_ace
          SuiteName.DIAMONDS -> R.mipmap.diamonds_ace
          SuiteName.HEARTS -> R.mipmap.hearts_ace
          SuiteName.SPADES -> R.mipmap.spades_ace_simple
        }
      }
      2 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_2
        SuiteName.DIAMONDS -> R.mipmap.diamonds_2
        SuiteName.HEARTS -> R.mipmap.hearts_2
        SuiteName.SPADES -> R.mipmap.spades_2
      }
      3 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_3
        SuiteName.DIAMONDS -> R.mipmap.diamonds_3
        SuiteName.HEARTS -> R.mipmap.hearts_3
        SuiteName.SPADES -> R.mipmap.spades_3
      }
      4 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_4
        SuiteName.DIAMONDS -> R.mipmap.diamonds_4
        SuiteName.HEARTS -> R.mipmap.hearts_4
        SuiteName.SPADES -> R.mipmap.spades_4
      }
      5 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_5
        SuiteName.DIAMONDS -> R.mipmap.diamonds_5
        SuiteName.HEARTS -> R.mipmap.hearts_5
        SuiteName.SPADES -> R.mipmap.spades_5
      }
      6 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_6
        SuiteName.DIAMONDS -> R.mipmap.diamonds_6
        SuiteName.HEARTS -> R.mipmap.hearts_6
        SuiteName.SPADES -> R.mipmap.spades_6
      }
      7 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_7
        SuiteName.DIAMONDS -> R.mipmap.diamonds_7
        SuiteName.HEARTS -> R.mipmap.hearts_7
        SuiteName.SPADES -> R.mipmap.spades_7
      }
      8 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_8
        SuiteName.DIAMONDS -> R.mipmap.diamonds_8
        SuiteName.HEARTS -> R.mipmap.hearts_8
        SuiteName.SPADES -> R.mipmap.spades_8
      }
      9 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_9
        SuiteName.DIAMONDS -> R.mipmap.diamonds_9
        SuiteName.HEARTS -> R.mipmap.hearts_9
        SuiteName.SPADES -> R.mipmap.spades_9
      }
      10 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_10
        SuiteName.DIAMONDS -> R.mipmap.diamonds_10
        SuiteName.HEARTS -> R.mipmap.hearts_10
        SuiteName.SPADES -> R.mipmap.spades_10
      }
      11 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_jack
        SuiteName.DIAMONDS -> R.mipmap.diamonds_jack
        SuiteName.HEARTS -> R.mipmap.hearts_jack
        SuiteName.SPADES -> R.mipmap.spades_jack
      }
      12 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_queen
        SuiteName.DIAMONDS -> R.mipmap.diamonds_queen
        SuiteName.HEARTS -> R.mipmap.hearts_queen
        SuiteName.SPADES -> R.mipmap.spades_queen
      }
      13 -> return when (suite) {
        SuiteName.CLUBS -> R.mipmap.clubs_king
        SuiteName.DIAMONDS -> R.mipmap.diamonds_king
        SuiteName.HEARTS -> R.mipmap.hearts_king
        SuiteName.SPADES -> R.mipmap.spades_king
      }
      else -> {
        throw Exception("Yikes!, this should not happen")
      }
    }
  }

  companion object {
    private const val LOWEST_CARD_VALUE = 2
    private const val HIGHEST_CARD_VALUE = 14
  }
}
