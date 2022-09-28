package com.jorgereyes.warofsuites.data.model

import androidx.annotation.IdRes


data class Card(
  val cardValue: Int,
  val cardName: String,
  val suit: Suite,
  val image: Int
)
