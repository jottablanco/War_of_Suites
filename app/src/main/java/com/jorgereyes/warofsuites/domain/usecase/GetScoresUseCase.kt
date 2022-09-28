package com.jorgereyes.warofsuites.domain.usecase

import com.jorgereyes.warofsuites.data.model.Player
import com.jorgereyes.warofsuites.domain.repository.ScoresRepository
import kotlinx.coroutines.flow.Flow

class GetScoresUseCase(private val scoresRepository: ScoresRepository) {

  fun execute(): Flow<List<Player>> {
    return scoresRepository.getScores()
  }
}
