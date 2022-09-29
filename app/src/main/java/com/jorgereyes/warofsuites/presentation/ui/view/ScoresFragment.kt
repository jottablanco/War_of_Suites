package com.jorgereyes.warofsuites.presentation.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jorgereyes.warofsuites.R
import com.jorgereyes.warofsuites.presentation.adapter.ScoresAdapter
import com.jorgereyes.warofsuites.presentation.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_scores.*


class ScoresFragment : Fragment() {

  private lateinit var mainViewModel: MainViewModel
  private lateinit var scoresAdapter: ScoresAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_scores, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mainViewModel = (activity as MainActivity).mainViewModel
    scoresAdapter = (activity as MainActivity).scoresAdapter

    setupUI()
  }

  private fun setupUI() {
    scoresAdapter.apply {
      setupScoresList()
    }
  }

  private fun setupScoresList() {
    mainViewModel.getScoresHistory().observe(viewLifecycleOwner) {
      if (it.isNotEmpty()) {
        scores_empty_message.visibility = View.GONE
        rv_scores.visibility = View.VISIBLE
        scoresAdapter.differ.submitList(it)
        initRecyclerView()
      }
    }
  }

  private fun initRecyclerView() {
    rv_scores.apply {
      adapter = scoresAdapter
      layoutManager = LinearLayoutManager(activity)
    }
  }
}
