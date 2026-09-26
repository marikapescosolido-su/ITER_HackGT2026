package com.iter.app.data.model

import java.time.LocalDate

/**
 * One daily check-in. [scores] holds only answered questions (0.0-10.0, continuous);
 * [skipped] is kept separately so "Skip" is never read as 0.
 */
data class CheckIn(
    val date: LocalDate,
    val scores: Map<Question, Float>,
    val sleepHours: Float,
    val tookMedication: Boolean,
    val sideEffects: Set<SideEffect> = emptySet(),
    val note: String = "",
    val durationSeconds: Int = 60,
    val skipped: Set<Question> = emptySet(),
    val appetiteDirection: AppetiteDirection? = null,
) {
    fun score(question: Question): Float? = scores[question]
}
