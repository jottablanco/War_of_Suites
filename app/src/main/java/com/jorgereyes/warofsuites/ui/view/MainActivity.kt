package com.jorgereyes.warofsuites.ui.view

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.jorgereyes.warofsuites.R
import com.jorgereyes.warofsuites.data.model.Card
import com.jorgereyes.warofsuites.data.model.Player
import com.jorgereyes.warofsuites.data.model.SuiteName
import com.jorgereyes.warofsuites.ui.base.ViewModelFactory
import com.jorgereyes.warofsuites.ui.main.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_1_table.*
import kotlinx.android.synthetic.main.player_2_table.*

class MainActivity : AppCompatActivity() {

  private lateinit var mainViewModel: MainViewModel
  private lateinit var suiteValuesMap: MutableMap<Int, SuiteName>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupViewModel()

    mainViewModel.shuffleMainDeck()
    suiteValuesMap = mainViewModel.defineSuiteValue()
    mainViewModel.suitesValueMap = suiteValuesMap
    mainViewModel.createPlayersDecks()

    setupUI()
  }

  private fun setupViewModel() {
    mainViewModel = ViewModelProviders.of(
      this@MainActivity,
      ViewModelFactory()
    )[MainViewModel::class.java]
  }

  private fun setupUI() {
    player_1_dealt_card.setImageResource(R.mipmap.astronaut)
    player_2_dealt_card.setImageResource(R.mipmap.astronaut)

    player_1_deck_counter.text = mainViewModel.getPlayerDeckSize(Player.PLAYER_1).toString()
    player_2_deck_counter.text = mainViewModel.getPlayerDeckSize(Player.PLAYER_2).toString()

    player_1_counter.text = getString(R.string.start_counter)
    player_2_counter.text = getString(R.string.start_counter)

    setupSuitesValeUI()

    deal_btn.setOnClickListener {

      if (!mainViewModel.checkForGameOver()) {
        val player1Card = mainViewModel.dealCard(Player.PLAYER_1)[Player.PLAYER_1]
        val player2Card = mainViewModel.dealCard(Player.PLAYER_2)[Player.PLAYER_2]

        player1Card?.let {
          player_1_dealt_card.setImageResource(it.image)
        } ?: kotlin.run {
          displayError()
        }

        player2Card?.let {
          player_2_dealt_card.setImageResource(it.image)
        } ?: kotlin.run {
          displayError()
        }


        player_1_deck_counter.text = mainViewModel.getPlayerDeckSize(Player.PLAYER_1).toString()
        player_2_deck_counter.text = mainViewModel.getPlayerDeckSize(Player.PLAYER_2).toString()

        getRoundWinner(player1Card!!, player2Card!!)
      } else {
        isGameOver()
      }

    }
  }

  private fun displayError() {
    Snackbar.make(this@MainActivity, parent_container, getString(R.string.deal_card_error), Snackbar.LENGTH_SHORT).show()
  }

  private fun getRoundWinner(player1Card: Card, player2Card: Card) {
    if (player1Card.cardValue > player2Card.cardValue) {
      updateUI(Player.PLAYER_1, player1Card, player2Card)
    } else if (player1Card.cardValue < player2Card.cardValue) {
      updateUI(Player.PLAYER_2, player1Card, player2Card)
    } else {
      val keyPlayer1 = suiteValuesMap.filterValues { it == player1Card.suit.suiteName }.keys.first()
      val keyPlayer2 = suiteValuesMap.filterValues { it == player2Card.suit.suiteName }.keys.first()

      if (keyPlayer1 > keyPlayer2) {
        updateUI(Player.PLAYER_1, player1Card, player2Card)
      } else {
        updateUI(Player.PLAYER_2, player1Card, player2Card)
      }
    }
  }

  private fun updateUI(player: Player, player1Card: Card, player2Card: Card) {
    when (player) {
      Player.PLAYER_1 -> {
        mainViewModel.addToPlayersDiscardsPile(player1Card, player2Card, Player.PLAYER_1)
        player_1_counter.text = "${mainViewModel.getPlayersRoundScore(Player.PLAYER_1)}"
      }
      Player.PLAYER_2 -> {
        mainViewModel.addToPlayersDiscardsPile(player2Card, player2Card, Player.PLAYER_2)
        player_2_counter.text = "${mainViewModel.getPlayersRoundScore(Player.PLAYER_2)}"
      }
    }
  }

  private fun isGameOver() {

    val message = getString(
      R.string.game_winner_label,
      mainViewModel.getWinner(),
      mainViewModel.getPlayersRoundScore(mainViewModel.getWinner()).toString()
    )

    if (mainViewModel.checkForGameOver()) {

      val alertDialog: AlertDialog? = this.let {
        val builder = AlertDialog.Builder(it)
        builder.apply {
          setPositiveButton(
            getString(R.string.game_over_dialog_restart_btn_label)
          ) { _, _ ->
            mainViewModel.restartGame()
            setupUI()
          }
          setMessage(message)
          setTitle(getString(R.string.game_over_dialog_title))
        }
        builder.create()
      }

      alertDialog!!.show()
    }
  }

  private fun setupSuitesValeUI() {
    for (item in suiteValuesMap) {
      if (item.key == KEY_4) {
        when (item.value) {
          SuiteName.CLUBS -> icon_1.setImageResource(R.mipmap.club_ic)
          SuiteName.HEARTS -> icon_1.setImageResource(R.mipmap.hearth_ic)
          SuiteName.SPADES -> icon_1.setImageResource(R.mipmap.spade_ic)
          SuiteName.DIAMONDS -> icon_1.setImageResource(R.mipmap.diamond_ic)
        }
      }
      if (item.key == KEY_3) {
        when (item.value) {
          SuiteName.CLUBS -> icon_2.setImageResource(R.mipmap.club_ic)
          SuiteName.HEARTS -> icon_2.setImageResource(R.mipmap.hearth_ic)
          SuiteName.SPADES -> icon_2.setImageResource(R.mipmap.spade_ic)
          SuiteName.DIAMONDS -> icon_2.setImageResource(R.mipmap.diamond_ic)
        }
      }
      if (item.key == KEY_2) {
        when (item.value) {
          SuiteName.CLUBS -> icon_3.setImageResource(R.mipmap.club_ic)
          SuiteName.HEARTS -> icon_3.setImageResource(R.mipmap.hearth_ic)
          SuiteName.SPADES -> icon_3.setImageResource(R.mipmap.spade_ic)
          SuiteName.DIAMONDS -> icon_3.setImageResource(R.mipmap.diamond_ic)
        }
      }
      if (item.key == KEY_1) {
        when (item.value) {
          SuiteName.CLUBS -> icon_4.setImageResource(R.mipmap.club_ic)
          SuiteName.HEARTS -> icon_4.setImageResource(R.mipmap.hearth_ic)
          SuiteName.SPADES -> icon_4.setImageResource(R.mipmap.spade_ic)
          SuiteName.DIAMONDS -> icon_4.setImageResource(R.mipmap.diamond_ic)
        }
      }
    }
  }

  companion object {
    private const val KEY_1 = 1
    private const val KEY_2 = 2
    private const val KEY_3 = 3
    private const val KEY_4 = 4
  }

}
