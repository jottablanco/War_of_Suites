package com.jorgereyes.warofsuites.domain.usecase

import com.jorgereyes.warofsuites.data.model.Player
import com.jorgereyes.warofsuites.domain.repository.ScoresRepository

class SaveWinnerUseCase(private val scoresRepository: ScoresRepository) {
  fun execute(player: Player) = scoresRepository.saveWinner(player)
}
