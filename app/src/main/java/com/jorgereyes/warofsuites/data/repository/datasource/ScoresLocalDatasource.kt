package com.jorgereyes.warofsuites.data.repository.datasource

import com.jorgereyes.warofsuites.data.model.Player
import kotlinx.coroutines.flow.Flow

interface ScoresLocalDatasource {
  fun saveWinner(player: Player)
  fun getScores() : Flow<List<Player>>
}
