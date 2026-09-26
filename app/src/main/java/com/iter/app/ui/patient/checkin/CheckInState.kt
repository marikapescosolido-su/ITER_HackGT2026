package com.iter.app.ui.patient.checkin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.AppetiteDirection
import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.Question
import com.iter.app.data.model.SideEffect
import com.iter.app.domain.MedicationProfiles
import kotlin.math.roundToInt

/** Answers for today's check-in, shared by all the step screens. */
class CheckInState {
    private val repo = DemoRepository
    val medication = repo.patient.monitoredMedication
    val profile = MedicationProfiles.forMedication(medication.name)
    val questions = Question.entries

    /** Only answered questions; nothing is preselected. */
    val scores = mutableStateMapOf<Question, Float>()
    val skipped = mutableStateMapOf<Question, Boolean>()
    var appetiteDirection by mutableStateOf<AppetiteDirection?>(null)
    var sleepHours by mutableFloatStateOf(7f)
    var tookMedication by mutableStateOf<Boolean?>(null)
    val sideEffects = mutableStateMapOf<SideEffect, Boolean>()
    var note by mutableStateOf("")
    private val startedAt = System.currentTimeMillis()

    fun answer(question: Question, value: Float) {
        scores[question] = value
        skipped.remove(question)
    }

    fun skip(question: Question) {
        scores.remove(question)
        skipped[question] = true
        if (question == Question.Appetite) appetiteDirection = null
    }

    fun save(): CheckIn {
        val checkIn = CheckIn(
            date = repo.today,
            scores = scores.mapValues { (it.value * 10).roundToInt() / 10f },
            sleepHours = sleepHours,
            tookMedication = tookMedication ?: false,
            sideEffects = sideEffects.filterValues { it }.keys,
            note = note.trim(),
            durationSeconds = ((System.currentTimeMillis() - startedAt) / 1000).toInt(),
            skipped = skipped.filterValues { it }.keys,
            appetiteDirection = appetiteDirection.takeIf { (scores[Question.Appetite] ?: 0f) > 0f },
        )
        repo.saveCheckIn(checkIn)
        return checkIn
    }
}
