package com.iter.app.ui.patient.checkin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.Question
import com.iter.app.data.model.SideEffect
import com.iter.app.domain.DailyQuestionPlan
import com.iter.app.domain.MedicationProfiles
import kotlin.math.roundToInt

/** Answers for today's check-in, shared by all the step screens. */
class CheckInState {
    private val repo = DemoRepository
    val medication = repo.patient.monitoredMedication
    val profile = MedicationProfiles.forMedication(medication.name)
    private val questions = DailyQuestionPlan.questionsFor(repo.today, profile)

    val emotionalQuestions = questions.filter { it in EMOTIONAL }
    val physicalQuestions = questions.filterNot { it in EMOTIONAL }

    val scores = mutableStateMapOf<Question, Float>().apply { questions.forEach { put(it, 5f) } }
    var sleepHours by mutableFloatStateOf(7f)
    var tookMedication by mutableStateOf<Boolean?>(null)
    val sideEffects = mutableStateMapOf<SideEffect, Boolean>()
    var note by mutableStateOf("")
    private val startedAt = System.currentTimeMillis()

    fun save(): CheckIn {
        val checkIn = CheckIn(
            date = repo.today,
            scores = scores.mapValues { it.value.roundToInt() },
            sleepHours = sleepHours,
            tookMedication = tookMedication ?: false,
            sideEffects = sideEffects.filterValues { it }.keys,
            note = note.trim(),
            durationSeconds = ((System.currentTimeMillis() - startedAt) / 1000).toInt(),
        )
        repo.saveCheckIn(checkIn)
        return checkIn
    }

    companion object {
        private val EMOTIONAL = setOf(
            Question.Mood, Question.Anxiety, Question.Energy,
            Question.Concentration, Question.Stress, Question.Connection,
        )
    }
}
