package com.jorgereyes.warofsuites.di

import android.app.Application
import com.jorgereyes.warofsuites.domain.usecase.GetScoresUseCase
import com.jorgereyes.warofsuites.domain.usecase.SaveWinnerUseCase
import com.jorgereyes.warofsuites.presentation.viewModel.MainViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

  @Singleton
  @Provides
  fun providesMainViewModelFactory(
    application: Application,
    saveWinnerUseCase: SaveWinnerUseCase,
    getScoresUseCase: GetScoresUseCase
  ): MainViewModelFactory {
    return MainViewModelFactory(
      application,
      getScoresUseCase,
      saveWinnerUseCase
    )
  }
}
