package com.iter.app.domain

import com.iter.app.data.model.Question
import com.iter.app.data.model.SideEffect

/**
 * What to monitor for each medication (README: "Medication-specific and personalized monitoring").
 * Demo values only; a real version needs clinician review.
 */
data class MedicationProfile(
    val medication: String,
    val sideEffects: List<SideEffect>,
    val priorityQuestions: List<Question>,
)

object MedicationProfiles {
    private val default = MedicationProfile(
        medication = "Other",
        sideEffects = listOf(SideEffect.Nausea, SideEffect.Tiredness, SideEffect.Headache, SideEffect.TroubleSleeping),
        priorityQuestions = emptyList(),
    )

    private val profiles = listOf(
        MedicationProfile(
            medication = "Sertraline",
            sideEffects = listOf(
                SideEffect.Nausea, SideEffect.Tiredness, SideEffect.Headache,
                SideEffect.TroubleSleeping, SideEffect.Dizziness, SideEffect.SexualFunction,
            ),
            priorityQuestions = listOf(Question.Appetite, Question.Physical),
        ),
        MedicationProfile(
            medication = "Bupropion",
            sideEffects = listOf(
                SideEffect.TroubleSleeping, SideEffect.Restlessness, SideEffect.Headache, SideEffect.AppetiteChange,
            ),
            priorityQuestions = listOf(Question.Stress),
        ),
        MedicationProfile(
            medication = "Mirtazapine",
            sideEffects = listOf(SideEffect.Tiredness, SideEffect.AppetiteChange, SideEffect.Dizziness),
            priorityQuestions = listOf(Question.Appetite, Question.DailyTasks),
        ),
    )

    fun forMedication(name: String): MedicationProfile =
        profiles.firstOrNull { it.medication.equals(name.trim(), ignoreCase = true) } ?: default
}
