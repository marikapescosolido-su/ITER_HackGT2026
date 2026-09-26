package com.iter.app.domain

import com.iter.app.data.model.Question
import java.time.LocalDate

/**
 * Which slider questions to ask on a given day: all core questions, plus a few secondary ones
 * that rotate so the check-in stays short. The medication profile's priority questions come first.
 */
object DailyQuestionPlan {
    private const val SECONDARY_PER_DAY = 2

    fun questionsFor(date: LocalDate, profile: MedicationProfile): List<Question> {
        val core = Question.entries.filter { it.isCore }
        val secondary = (profile.priorityQuestions + Question.entries.filter { !it.isCore })
            .distinct()
            .filterNot { it.isCore }
        val offset = (date.toEpochDay() % secondary.size).toInt()
        val rotated = List(SECONDARY_PER_DAY) { secondary[(offset + it) % secondary.size] }
        return core + rotated
    }
}
