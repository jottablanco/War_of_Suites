package com.jorgereyes.warofsuites.data.model

data class Suite(
  val suiteName: SuiteName,
  val suiteValue: Int? = 0
)


enum class SuiteName {
  CLUBS,
  DIAMONDS,
  HEARTS,
  SPADES
}
