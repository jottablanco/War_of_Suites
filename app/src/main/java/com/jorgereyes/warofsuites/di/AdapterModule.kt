package com.jorgereyes.warofsuites.di

import com.jorgereyes.warofsuites.presentation.adapter.ScoresAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

  @Singleton
  @Provides
  fun providesScoresAdapter(): ScoresAdapter {
    return ScoresAdapter()
  }

}
