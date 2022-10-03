package com.jorgereyes.warofsuites

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.jorgereyes.warofsuites.data.db.PlayerDAO
import com.jorgereyes.warofsuites.data.db.ScoresDatabase
import com.jorgereyes.warofsuites.data.model.Player
import com.jorgereyes.warofsuites.data.model.PlayerName
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

@RunWith(JUnit4::class)
class DatabaseTest {

  private lateinit var database: ScoresDatabase
  private lateinit var scoresDao: PlayerDAO


  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()

    database = Room.inMemoryDatabaseBuilder(
      context,
      ScoresDatabase::class.java,
    ).allowMainThreadQueries().build()

    scoresDao = database.getPlayerDAO()
  }

  @After
  @Throws(IOException::class)
  fun tearDownDB() {
    database.close()
  }

  @Test
  @Throws(Exception::class)
  fun test_writeWinnerAndRetrieveIt() = runBlocking {
    val player = Player(UUID.randomUUID(), PlayerName.PLAYER_1, 10)
    scoresDao.insertWinner(player)

    val latch = CountDownLatch(1)

    val job = async(Dispatchers.IO) {
      scoresDao.getScores().collect {
        assert(player.playerTag == it.first().playerTag)
        latch.countDown()
      }
    }

    withContext(Dispatchers.IO) {
      latch.await()
    }
    job.cancelAndJoin()
  }

}
