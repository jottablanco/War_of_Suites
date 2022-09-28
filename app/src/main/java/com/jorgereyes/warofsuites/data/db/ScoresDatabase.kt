package com.jorgereyes.warofsuites.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jorgereyes.warofsuites.data.model.Player

@Database(
  entities = [Player::class],
  version = 1,
  exportSchema = false
)
abstract class ScoresDatabase : RoomDatabase() {
  abstract fun getPlayerDAO(): PlayerDAO
}
