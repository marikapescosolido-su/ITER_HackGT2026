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
        for (q in listOf(Question.Mood, Question.Tension, Question.Exhaustion, Question.Sleep)) {
            changeSentence(q, report.average(q), report.previousAverage(q))?.let(::add)
        }

        val missed = report.checkIns.filter { !it.tookMedication }
        val taken = report.checkIns.filter { it.tookMedication }
        val tensionMissed = report.average(Question.Tension, missed)
        val tensionTaken = report.average(Question.Tension, taken)
        if (tensionMissed != null && tensionTaken != null && tensionMissed - tensionTaken >= 1f) {
            add(
                "Tension was higher on days a dose was missed (${fmt(tensionMissed)} vs ${fmt(tensionTaken)}). " +
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
            // The supporter rates mood (higher = better); the patient rates low mood (higher = heavier).
            val lowMood = report.average(Question.Mood)
            if (lowMood != null && abs(obs.mood - (10f - lowMood)) >= 2f) {
                add("${obs.supporterName}'s view of mood (${obs.mood}/10, higher = better) differs from the patient's own low-mood average (${fmt(lowMood)}/10, higher = heavier).")
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
