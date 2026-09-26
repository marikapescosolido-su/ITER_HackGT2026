package com.iter.app.data.model

import java.time.LocalDate

/** One daily check-in (1-3 minutes). Only the questions asked that day appear in [scores]. */
data class CheckIn(
    val date: LocalDate,
    val scores: Map<Question, Int>,
    val sleepHours: Float,
    val tookMedication: Boolean,
    val sideEffects: Set<SideEffect> = emptySet(),
    val note: String = "",
    val durationSeconds: Int = 60,
) {
    fun score(question: Question): Int? = scores[question]
}
