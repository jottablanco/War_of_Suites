package com.jorgereyes.warofsuites.presentation.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.jorgereyes.warofsuites.R
import com.jorgereyes.warofsuites.presentation.adapter.ScoresAdapter
import com.jorgereyes.warofsuites.presentation.viewModel.MainViewModel
import com.jorgereyes.warofsuites.presentation.viewModel.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var factory: MainViewModelFactory

  @Inject
  lateinit var scoresAdapter: ScoresAdapter

  lateinit var mainViewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
    val navController = navHostFragment.navController

    setupViewModel()
  }

  private fun setupViewModel() {
    mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
  }
}
