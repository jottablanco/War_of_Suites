package com.jorgereyes.warofsuites.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jorgereyes.warofsuites.data.model.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDAO {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertWinner(player: Player)

  @Query("SELECT * FROM scoresTable")
  fun getScores() : Flow<List<Player>>

}
