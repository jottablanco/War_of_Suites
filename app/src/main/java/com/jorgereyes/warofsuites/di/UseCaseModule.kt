package com.jorgereyes.warofsuites.di

import com.jorgereyes.warofsuites.domain.repository.ScoresRepository
import com.jorgereyes.warofsuites.domain.usecase.GetScoresUseCase
import com.jorgereyes.warofsuites.domain.usecase.SaveWinnerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

  @Singleton
  @Provides
  fun provideSaveWinnerUseCase(scoresRepository: ScoresRepository): SaveWinnerUseCase {
    return SaveWinnerUseCase(scoresRepository)
  }

  @Singleton
  @Provides
  fun provideGetScoresUseCase(scoresRepository: ScoresRepository): GetScoresUseCase {
    return GetScoresUseCase(scoresRepository)
  }

}
