package com.jorgereyes.warofsuites.data.repository

import com.jorgereyes.warofsuites.data.model.Player
import com.jorgereyes.warofsuites.data.repository.datasource.ScoresLocalDatasource
import com.jorgereyes.warofsuites.domain.repository.ScoresRepository
import kotlinx.coroutines.flow.Flow

class ScoresRepositoryImpl(private val scoresLocalDatasource: ScoresLocalDatasource) : ScoresRepository {
  override fun saveWinner(player: Player) {
    scoresLocalDatasource.saveWinner(player)
  }

  override fun getScores(): Flow<List<Player>> {
    return scoresLocalDatasource.getScores()
  }
}
