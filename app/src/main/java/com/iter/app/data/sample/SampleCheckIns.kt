package com.iter.app.data.sample

import com.iter.app.data.model.AppetiteDirection
import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.Phq9
import com.iter.app.data.model.Question
import com.iter.app.data.model.SideEffect
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * A believable story for the demo: low mood eases slowly, exhaustion peaks in weeks 2-4,
 * tension is higher on days a dose was missed, and two days are missing (shown as gaps).
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
            ((center + (rng.nextFloat() * 2 - 1) * spread).coerceIn(0f, 10f) * 10).roundToInt() / 10f

        return (0 until SamplePatient.TOTAL_DAYS).filterNot { it in missingDays }.map { day ->
            val date = SamplePatient.start.plusDays(day.toLong())
            val progress = day / SamplePatient.TOTAL_DAYS.toFloat()
            val tired = day in 7..27
            val tookMedication = rng.nextFloat() > 0.1f
            val missedPenalty = if (tookMedication) 0f else 2f

            val scores = mapOf(
                Question.Mood to around(7f - 4f * progress),
                Question.Enjoyment to around(7f - 4f * progress),
                Question.Sleep to around(6f - 3f * progress),
                Question.Appetite to around(if (day < 10) 6f else 2.5f),
                Question.Tension to around(6f - 3f * progress + missedPenalty),
                Question.Reading to around(6f - 3f * progress),
                Question.Messages to around(7f - 4f * progress),
                Question.Digestion to around(if (day < 10) 5f else 1.5f),
                Question.Irritability to around(5f - 2f * progress + missedPenalty),
                Question.LosingTrack to around(5f - 2.5f * progress),
                Question.Exhaustion to around(if (tired) 7f else 6f - 2.5f * progress),
                Question.Conversations to around(5f - 2.5f * progress),
                Question.Concentration to around(6.5f - 3f * progress),
                Question.SeeingFriends to around(7f - 4f * progress),
                Question.EverydayBasics to around(6.5f - 3.5f * progress),
                Question.RevvedUp to around(0.5f, spread = 0.5f),
                Question.SelfHarm to 0f,
                Question.JawNeckTension to around(5f - 2f * progress + missedPenalty),
                Question.ChillsHotFlashes to around(2f - 1f * progress, spread = 1f),
            )
            CheckIn(
                date = date,
                scores = scores,
                appetiteDirection = if (day < 10) AppetiteDirection.Less else null,
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
