package com.jorgereyes.warofsuites.presentation.ui.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jorgereyes.warofsuites.R
import com.jorgereyes.warofsuites.data.model.Card
import com.jorgereyes.warofsuites.data.model.Player
import com.jorgereyes.warofsuites.data.model.PlayerName
import com.jorgereyes.warofsuites.data.model.SuiteName
import com.jorgereyes.warofsuites.presentation.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.player_1_table.*
import kotlinx.android.synthetic.main.player_2_table.*
import java.util.*

class GameFragment : Fragment() {

  private lateinit var mainViewModel: MainViewModel

  private lateinit var suiteValuesMap: MutableMap<Int, SuiteName>

  private val player1 = Player(id = UUID.randomUUID(), PlayerName.PLAYER_1)
  private val player2 = Player(id = UUID.randomUUID(), PlayerName.PLAYER_2)


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_game, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mainViewModel = (activity as MainActivity).mainViewModel
    mainViewModel.shuffleMainDeck()
    suiteValuesMap = mainViewModel.defineSuiteValue()
    mainViewModel.suitesValueMap = suiteValuesMap
    mainViewModel.createPlayersDecks()
    setupUI()
  }

  private fun setupUI() {
    player_1_dealt_card.setImageResource(R.mipmap.astronaut)
    player_2_dealt_card.setImageResource(R.mipmap.astronaut)

    player_1_deck_counter.text = mainViewModel.getPlayerDeckSize(player1).toString()
    player_2_deck_counter.text = mainViewModel.getPlayerDeckSize(player2).toString()

    player_1_counter.text = getString(R.string.start_counter)
    player_2_counter.text = getString(R.string.start_counter)

    setupSuitesValeUI()

    winners_btn.setOnClickListener {
      findNavController().navigate(R.id.action_gameFragment_to_scoresFragment)
    }

    deal_btn.setOnClickListener {

      if (!mainViewModel.checkForGameOver()) {
        val player1Card = mainViewModel.dealCard(player1)[player1]
        val player2Card = mainViewModel.dealCard(player2)[player2]

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

        player_1_deck_counter.text = mainViewModel.getPlayerDeckSize(player1).toString()
        player_2_deck_counter.text = mainViewModel.getPlayerDeckSize(player2).toString()

        getRoundWinner(player1Card!!, player2Card!!)
      } else {
        isGameOver()
      }
    }
  }

  private fun displayError() {
    Snackbar.make(requireContext(), parent_container, getString(R.string.deal_card_error), Snackbar.LENGTH_SHORT).show()
  }

  private fun getRoundWinner(player1Card: Card, player2Card: Card) {
    if (player1Card.cardValue > player2Card.cardValue) {
      updateUI(player1, player1Card, player2Card)
    } else if (player1Card.cardValue < player2Card.cardValue) {
      updateUI(player2, player1Card, player2Card)
    } else {
      val keyPlayer1 = suiteValuesMap.filterValues { it == player1Card.suit.suiteName }.keys.first()
      val keyPlayer2 = suiteValuesMap.filterValues { it == player2Card.suit.suiteName }.keys.first()

      if (keyPlayer1 > keyPlayer2) {
        updateUI(player1, player1Card, player2Card)
      } else {
        updateUI(player2, player1Card, player2Card)
      }
    }
  }

  private fun updateUI(player: Player, player1Card: Card, player2Card: Card) {
    when (player.playerTag) {
      PlayerName.PLAYER_1 -> {
        mainViewModel.addToPlayersDiscardsPile(player1Card, player2Card, player1)
        player_1_counter.text = "${mainViewModel.getPlayersRoundScore(player1)}"
      }
      PlayerName.PLAYER_2 -> {
        mainViewModel.addToPlayersDiscardsPile(player2Card, player2Card, player2)
        player_2_counter.text = "${mainViewModel.getPlayersRoundScore(player2)}"
      }
    }
  }

  private fun isGameOver() {

    val winnerName = mainViewModel.getWinner()

    val winner = if (winnerName == PlayerName.PLAYER_1) {
      player1
    } else {
      player2
    }

    val winnerScore = mainViewModel.getPlayersRoundScore(winner)

    //Set score to later save the Winner in Room
    winner.finalScore = winnerScore

    val message = getString(
      R.string.game_winner_label,
      winner.playerTag,
      winnerScore.toString()
    )

    if (mainViewModel.checkForGameOver()) {

      val alertDialog: AlertDialog? = this.let {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
          setPositiveButton(
            getString(R.string.game_over_dialog_restart_btn_label)
          ) { _, _ ->
            mainViewModel.saveGameWinner(winner)
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
