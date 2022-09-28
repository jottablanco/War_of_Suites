package com.jorgereyes.warofsuites.data.repository.datasourceImpl

import com.jorgereyes.warofsuites.data.db.PlayerDAO
import com.jorgereyes.warofsuites.data.model.Player
import com.jorgereyes.warofsuites.data.repository.datasource.ScoresLocalDatasource
import kotlinx.coroutines.flow.Flow

class ScoresLocalDatasourceImpl(private val playerDAO: PlayerDAO) : ScoresLocalDatasource {

  override fun saveWinner(player: Player) {
    return playerDAO.insertWinner(player)
  }

  override fun getScores(): Flow<List<Player>> {
    return playerDAO.getScores()
  }
}
