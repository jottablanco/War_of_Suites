package com.jorgereyes.warofsuites.domain.repository

import com.jorgereyes.warofsuites.data.model.Player
import kotlinx.coroutines.flow.Flow

interface ScoresRepository {
  fun saveWinner(player: Player)
  fun getScores(): Flow<List<Player>>
}
