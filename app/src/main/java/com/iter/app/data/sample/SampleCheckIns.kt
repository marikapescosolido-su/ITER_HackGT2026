package com.iter.app.data.sample

import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.Phq9
import com.iter.app.data.model.Question
import com.iter.app.data.model.SideEffect
import com.iter.app.domain.DailyQuestionPlan
import com.iter.app.domain.MedicationProfiles
import kotlin.random.Random

/**
 * A believable story for the demo: mood improves slowly, tiredness peaks in weeks 2-4,
 * anxiety is higher on days a dose was missed, and two days are missing (shown as gaps).
 * Today is left empty so the patient can check in live during the demo.
 */
object SampleCheckIns {
    private val missingDays = setOf(17, 33)
    private val notes = mapOf(
        3 to "Felt sick to my stomach after the morning dose.",
        12 to "Slept most of the afternoon.",
        24 to "Went for a walk with Sam, first time in a while.",
        38 to "Busy day at work but it was OK.",
    )

    fun checkIns(): List<CheckIn> {
        val rng = Random(7)
        fun around(center: Float, spread: Float = 1.2f) =
            (center + (rng.nextFloat() * 2 - 1) * spread).toInt().coerceIn(0, 10)

        val profile = MedicationProfiles.forMedication(SamplePatient.patient.monitoredMedication.name)
        return (0 until SamplePatient.TOTAL_DAYS).filterNot { it in missingDays }.map { day ->
            val date = SamplePatient.start.plusDays(day.toLong())
            val progress = day / SamplePatient.TOTAL_DAYS.toFloat()
            val tired = day in 7..27
            val tookMedication = rng.nextFloat() > 0.1f
            val missedPenalty = if (tookMedication) 0f else 2f

            val all = mapOf(
                Question.Mood to around(3f + 4f * progress),
                Question.Anxiety to around(7f - 3f * progress + missedPenalty),
                Question.Energy to around(if (tired) 3f else 4f + 2.5f * progress),
                Question.SleepQuality to around(4f + 3f * progress),
                Question.Concentration to around(3.5f + 3f * progress),
                Question.Appetite to around(if (day < 10) 4f else 6.5f),
                Question.Stress to around(6f - 2f * progress),
                Question.Connection to around(3f + 4f * progress),
                Question.DailyTasks to around(3.5f + 3.5f * progress),
                Question.Physical to around(if (day < 10) 4f else 6f),
            )
            val asked = DailyQuestionPlan.questionsFor(date, profile)
            CheckIn(
                date = date,
                scores = all.filterKeys { it in asked },
                sleepHours = (5.5f + 1.5f * progress + (rng.nextFloat() - 0.5f)).coerceIn(3f, 10f),
                tookMedication = tookMedication,
                sideEffects = buildSet {
                    if (tired && rng.nextFloat() > 0.3f) add(SideEffect.Tiredness)
                    if (day < 10 && rng.nextFloat() > 0.5f) add(SideEffect.Nausea)
                    if (rng.nextFloat() > 0.88f) add(SideEffect.Headache)
                },
                note = notes[day].orEmpty(),
                durationSeconds = 50 + rng.nextInt(90),
            )
        }
    }

    fun phq9s(): List<Phq9> = listOf(19, 17, 15, 13, 10, 8).mapIndexed { week, total ->
        Phq9(SamplePatient.start.plusWeeks(week.toLong()), spread(total))
    }

    // Spreads a total over the 9 answers (0..3 each), keeping item 9 at 0 for the demo patient.
    private fun spread(total: Int): List<Int> {
        val answers = MutableList(9) { 0 }
        var left = total
        var i = 0
        while (left > 0) {
            val q = i % 8
            if (answers[q] < 3) {
                answers[q]++
                left--
            }
            i++
        }
        return answers
    }
}
