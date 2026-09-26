package com.iter.app.domain

import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.DoseChange
import com.iter.app.data.model.Patient
import com.iter.app.data.model.Phq9
import com.iter.app.data.model.Question
import com.iter.app.data.model.SideEffect
import com.iter.app.data.model.SupporterObservation
import java.time.LocalDate

/** Everything the doctor report shows (on screen and in the PDF), computed from raw data. */
data class WeeklyReport(
    val patient: Patient,
    val start: LocalDate,
    val end: LocalDate,
    val days: List<LocalDate>,
    val checkIns: List<CheckIn>,
    val previousCheckIns: List<CheckIn>,
    val missingDays: List<LocalDate>,
    val adherencePercent: Int,
    val sideEffectCounts: Map<SideEffect, Int>,
    val phq9s: List<Phq9>,
    val observations: List<SupporterObservation>,
    val doseChangesInPeriod: List<DoseChange>,
    val safetyFlags: List<String>,
) {
    val completedDays: Int get() = checkIns.size

    /** One value per day in [days]; null = no answer that day (drawn as a gap, never guessed). */
    fun daily(question: Question): List<Float?> =
        days.map { day -> checkIns.firstOrNull { it.date == day }?.score(question)?.toFloat() }

    fun dailySleepHours(): List<Float?> = days.map { day -> checkIns.firstOrNull { it.date == day }?.sleepHours }

    fun average(question: Question, list: List<CheckIn> = checkIns): Float? =
        list.mapNotNull { it.score(question) }.takeIf { it.isNotEmpty() }?.average()?.toFloat()

    fun previousAverage(question: Question): Float? = average(question, previousCheckIns)

    val notes: List<Pair<LocalDate, String>> get() = checkIns.filter { it.note.isNotBlank() }.map { it.date to it.note }
}

object ReportBuilder {
    fun build(
        patient: Patient,
        checkIns: List<CheckIn>,
        phq9s: List<Phq9>,
        observations: List<SupporterObservation>,
        windowDays: Int,
        includeSupporter: Boolean,
        end: LocalDate = LocalDate.now(),
    ): WeeklyReport {
        val start = end.minusDays(windowDays.toLong() - 1)
        val days = (0 until windowDays).map { start.plusDays(it.toLong()) }
        val inPeriod = checkIns.filter { it.date in days }
        val previousStart = start.minusDays(windowDays.toLong())
        val previous = checkIns.filter { !it.date.isBefore(previousStart) && it.date.isBefore(start) }

        val flags = buildList {
            phq9s.filter { it.needsSafetyFollowUp && !it.date.isBefore(start) }.forEach {
                add("PHQ-9 item 9 answered above 0 on ${it.date}. Please follow up.")
            }
        }

        return WeeklyReport(
            patient = patient,
            start = start,
            end = end,
            days = days,
            checkIns = inPeriod,
            previousCheckIns = previous,
            // Today isn't "missing" yet: the patient may still check in.
            missingDays = days.filter { day -> day != end && inPeriod.none { it.date == day } },
            adherencePercent = if (inPeriod.isEmpty()) 0 else inPeriod.count { it.tookMedication } * 100 / inPeriod.size,
            sideEffectCounts = inPeriod.flatMap { it.sideEffects }.groupingBy { it }.eachCount()
                .toList().sortedByDescending { it.second }.toMap(),
            phq9s = phq9s,
            observations = if (includeSupporter) observations.filter { !it.date.isAfter(end) } else emptyList(),
            doseChangesInPeriod = patient.doseChanges.filter { !it.date.isBefore(previousStart) },
            safetyFlags = flags,
        )
    }
}
