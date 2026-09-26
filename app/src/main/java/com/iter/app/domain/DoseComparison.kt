package com.iter.app.domain

import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.DoseChange
import com.iter.app.data.model.Question
import com.iter.app.data.model.SideEffect

/** Averages in the days before vs after a dose change, for the clinician's Medication context screen. */
data class DoseComparison(
    val change: DoseChange,
    val days: Int,
    val before: Map<Question, Float?>,
    val after: Map<Question, Float?>,
    val sideEffectsBefore: Map<SideEffect, Int>,
    val sideEffectsAfter: Map<SideEffect, Int>,
) {
    companion object {
        val QUESTIONS = listOf(Question.Mood, Question.Anxiety, Question.Energy, Question.SleepQuality)

        fun build(change: DoseChange, checkIns: List<CheckIn>, days: Int = 14): DoseComparison {
            val before = checkIns.filter { it.date.isBefore(change.date) && !it.date.isBefore(change.date.minusDays(days.toLong())) }
            val after = checkIns.filter { !it.date.isBefore(change.date) && it.date.isBefore(change.date.plusDays(days.toLong())) }
            fun avg(list: List<CheckIn>, q: Question) =
                list.mapNotNull { it.score(q) }.takeIf { it.isNotEmpty() }?.average()?.toFloat()
            fun effects(list: List<CheckIn>) = list.flatMap { it.sideEffects }.groupingBy { it }.eachCount()
            return DoseComparison(
                change, days,
                QUESTIONS.associateWith { avg(before, it) },
                QUESTIONS.associateWith { avg(after, it) },
                effects(before), effects(after),
            )
        }
    }
}
