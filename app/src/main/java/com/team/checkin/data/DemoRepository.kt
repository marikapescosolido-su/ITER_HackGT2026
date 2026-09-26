package com.team.checkin.data

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDate
import kotlin.random.Random

/**
 * In-memory data for the demo. Nothing is saved; restarting the app resets to the sample patient.
 * Screens read these lists directly and recompose when they change.
 */
object DemoRepository {
    val patient = Patient(
        name = "Alex",
        medication = "Sertraline",
        dose = "50 mg",
        treatmentStart = LocalDate.now().minusDays(41),
    )

    val checkIns = mutableStateListOf<CheckIn>().apply { addAll(SampleData.checkIns(patient.treatmentStart)) }
    val phq9s = mutableStateListOf<Phq9>().apply { addAll(SampleData.phq9s(patient.treatmentStart)) }
    val partnerCheckIns = mutableStateListOf<PartnerCheckIn>().apply {
        addAll(SampleData.partnerCheckIns(patient.treatmentStart))
    }

    fun addCheckIn(checkIn: CheckIn) {
        checkIns.removeAll { it.date == checkIn.date }
        checkIns.add(checkIn)
    }

    fun addPhq9(phq9: Phq9) {
        phq9s.removeAll { it.weekStart == phq9.weekStart }
        phq9s.add(phq9)
    }

    fun addPartnerCheckIn(checkIn: PartnerCheckIn) {
        partnerCheckIns.add(checkIn)
    }

    /** Consecutive days up to today with a check-in. */
    val streak: Int
        get() {
            val dates = checkIns.map { it.date }.toSet()
            var day = LocalDate.now()
            if (day !in dates) day = day.minusDays(1)
            var count = 0
            while (day in dates) {
                count++
                day = day.minusDays(1)
            }
            return count
        }
}

/** A believable 6-week story: mood slowly improves, tiredness peaks in weeks 2-4. */
private object SampleData {
    private val rng = Random(42)

    private fun jitter(value: Float, spread: Float = 1f) = value + (rng.nextFloat() * 2 - 1) * spread

    fun checkIns(start: LocalDate): List<CheckIn> = (0 until 41).map { day ->
        val progress = day / 41f
        val fatigueWeeks = day in 7..28
        CheckIn(
            date = start.plusDays(day.toLong()),
            mood = jitter(3f + 4f * progress).toInt().coerceIn(0, 10),
            energy = jitter(if (fatigueWeeks) 3f else 4f + 2f * progress).toInt().coerceIn(0, 10),
            anxiety = jitter(7f - 3f * progress).toInt().coerceIn(0, 10),
            sleepHours = jitter(5.5f + 1.5f * progress, 0.7f).coerceIn(3f, 10f),
            sleepQuality = jitter(4f + 3f * progress).toInt().coerceIn(0, 10),
            tookMedication = rng.nextFloat() > 0.1f,
            sideEffects = buildSet {
                if (fatigueWeeks && rng.nextFloat() > 0.3f) add(SideEffect.Fatigue)
                if (day < 10 && rng.nextFloat() > 0.6f) add(SideEffect.Nausea)
                if (rng.nextFloat() > 0.85f) add(SideEffect.Headache)
            },
        )
    }

    fun phq9s(start: LocalDate): List<Phq9> {
        val totals = listOf(19, 17, 15, 12, 10, 8)
        return totals.mapIndexed { week, total -> Phq9(start.plusWeeks(week.toLong()), spread(total)) }
    }

    // Spread a total over 9 answers (0..3 each), keeping question 9 at 0 for the demo patient.
    private fun spread(total: Int): List<Int> {
        val answers = MutableList(9) { 0 }
        var left = total
        var i = 0
        while (left > 0) {
            if (i % 9 != 8 && answers[i % 9] < 3) {
                answers[i % 9]++
                left--
            }
            i++
        }
        return answers
    }

    fun partnerCheckIns(start: LocalDate): List<PartnerCheckIn> = (0 until 6).map { week ->
        PartnerCheckIn(
            weekStart = start.plusWeeks(week.toLong()),
            partnerName = "Sam",
            seemsMood = (3 + week).coerceAtMost(10),
            seemsEnergy = if (week in 1..3) 3 else 5,
            note = if (week == 3) "Seems more like themselves, but tired all the time." else "",
        )
    }
}
