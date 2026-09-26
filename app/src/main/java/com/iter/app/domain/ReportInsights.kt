package com.iter.app.domain

import com.iter.app.data.model.Question
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * The "AI-assisted summary" section of the report.
 * For the demo this is rule-based. To use a real LLM later, send the WeeklyReport numbers to it
 * from a backend (never put an API key in the app) and keep the same rules:
 * describe observations, never causes, and never recommend a medication.
 */
object ReportInsights {

    fun summarize(report: WeeklyReport): List<String> = buildList {
        for (q in listOf(Question.Mood, Question.Anxiety, Question.Energy, Question.SleepQuality)) {
            changeSentence(q, report.average(q), report.previousAverage(q))?.let(::add)
        }

        val missed = report.checkIns.filter { !it.tookMedication }
        val taken = report.checkIns.filter { it.tookMedication }
        val anxietyMissed = report.average(Question.Anxiety, missed)
        val anxietyTaken = report.average(Question.Anxiety, taken)
        if (anxietyMissed != null && anxietyTaken != null && anxietyMissed - anxietyTaken >= 1f) {
            add(
                "Anxiety was higher on days a dose was missed (${fmt(anxietyMissed)} vs ${fmt(anxietyTaken)}). " +
                    "This is an association, not proof of cause.",
            )
        }

        report.sideEffectCounts.entries.firstOrNull()?.let { (effect, count) ->
            add("Most reported side effect: ${effect.label.lowercase()} ($count of ${report.completedDays} check-ins).")
        }

        val latestPhq = report.phq9s.lastOrNull()
        val firstPhq = report.phq9s.firstOrNull()
        if (latestPhq != null && firstPhq != null && latestPhq != firstPhq) {
            add("PHQ-9 went from ${firstPhq.total} (${firstPhq.severity.lowercase()}) to ${latestPhq.total} (${latestPhq.severity.lowercase()}) since treatment started.")
        }

        report.observations.lastOrNull()?.let { obs ->
            val patientMood = report.average(Question.Mood)
            if (patientMood != null && abs(obs.mood - patientMood) >= 2f) {
                add("${obs.supporterName}'s view of mood (${obs.mood}/10) differs from the patient's own average (${fmt(patientMood)}/10).")
            }
        }

        if (report.missingDays.isNotEmpty()) {
            add("${report.missingDays.size} day(s) have no check-in. Missing days are shown as gaps and are not counted as good or bad.")
        }
    }

    private fun changeSentence(q: Question, now: Float?, before: Float?): String? {
        if (now == null || before == null) return null
        val diff = now - before
        if (abs(diff) < 0.5f) return "${q.label} was about the same as the previous period (${fmt(now)}/10)."
        val direction = if (diff > 0) "higher" else "lower"
        return "${q.label} averaged ${fmt(now)}/10, $direction than the previous period (${fmt(before)})."
    }

    private fun fmt(value: Float) = ((value * 10).roundToInt() / 10f).toString()
}
