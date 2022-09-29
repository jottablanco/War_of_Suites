package com.jorgereyes.warofsuites.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jorgereyes.warofsuites.domain.usecase.GetScoresUseCase
import com.jorgereyes.warofsuites.domain.usecase.SaveWinnerUseCase

class MainViewModelFactory(
  private val application: Application,
  private val getScoresUseCase: GetScoresUseCase,
  private val saveWinnerUseCase: SaveWinnerUseCase
) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return MainViewModel(
      application,
      getScoresUseCase,
      saveWinnerUseCase
    ) as T
  }
}
