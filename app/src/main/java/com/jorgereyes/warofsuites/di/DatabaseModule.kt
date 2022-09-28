package com.jorgereyes.warofsuites.di

import android.app.Application
import androidx.room.Room
import com.jorgereyes.warofsuites.data.db.PlayerDAO
import com.jorgereyes.warofsuites.data.db.ScoresDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Singleton
  @Provides
  fun provideScoresDatabase(app: Application): ScoresDatabase {
    return Room.databaseBuilder(app, ScoresDatabase::class.java, "scores_db")
      .fallbackToDestructiveMigration()
      .allowMainThreadQueries()
      .build()
  }

  @Singleton
  @Provides
  fun providePlayerDAO(scoresDatabase: ScoresDatabase): PlayerDAO {
    return scoresDatabase.getPlayerDAO()
  }

}
