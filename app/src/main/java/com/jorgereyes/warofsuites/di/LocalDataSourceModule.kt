package com.jorgereyes.warofsuites.di

import com.jorgereyes.warofsuites.data.db.PlayerDAO
import com.jorgereyes.warofsuites.data.repository.datasource.ScoresLocalDatasource
import com.jorgereyes.warofsuites.data.repository.datasourceImpl.ScoresLocalDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

  @Singleton
  @Provides
  fun provideLocalDataSource(playerDAO: PlayerDAO) : ScoresLocalDatasource {
    return ScoresLocalDatasourceImpl(playerDAO)
  }
}
