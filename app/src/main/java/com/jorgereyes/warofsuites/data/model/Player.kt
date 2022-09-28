package com.jorgereyes.warofsuites.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "scoresTable")
data class Player(
  @PrimaryKey
  var id: UUID,
  val playerTag: PlayerName,
  var finalScore: Int? = 0
) : Serializable

enum class PlayerName {
  PLAYER_1,
  PLAYER_2
}
