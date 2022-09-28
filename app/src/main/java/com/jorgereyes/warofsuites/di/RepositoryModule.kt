package com.jorgereyes.warofsuites.di

import com.jorgereyes.warofsuites.data.repository.ScoresRepositoryImpl
import com.jorgereyes.warofsuites.data.repository.datasource.ScoresLocalDatasource
import com.jorgereyes.warofsuites.domain.repository.ScoresRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

  @Singleton
  @Provides
  fun provideScoreRepository(scoresLocalDatasource: ScoresLocalDatasource): ScoresRepository {
    return ScoresRepositoryImpl(scoresLocalDatasource)
  }
}
