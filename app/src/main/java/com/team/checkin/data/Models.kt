package com.team.checkin.data

import java.time.LocalDate

// Shared data types. Agree as a team before changing these; every screen depends on them.

data class Patient(
    val name: String,
    val medication: String,
    val dose: String,
    val treatmentStart: LocalDate,
)

enum class SideEffect(val label: String) {
    Nausea("Nausea"),
    Fatigue("Tiredness"),
    Headache("Headache"),
    Insomnia("Trouble sleeping"),
    Restlessness("Restlessness"),
    Appetite("Appetite change"),
}

/** One daily check-in (about 3 minutes). Scales are 0 (worst) to 10 (best), except anxiety. */
data class CheckIn(
    val date: LocalDate,
    val mood: Int,
    val energy: Int,
    val anxiety: Int, // 0 = calm, 10 = very anxious
    val sleepHours: Float,
    val sleepQuality: Int,
    val tookMedication: Boolean,
    val sideEffects: Set<SideEffect> = emptySet(),
    val note: String = "",
)

/** Weekly PHQ-9. Each answer is 0..3; total is 0..27. */
data class Phq9(
    val weekStart: LocalDate,
    val answers: List<Int>,
) {
    val total: Int get() = answers.sum()

    /** Question 9 asks about thoughts of self-harm; any answer above 0 must trigger the safety flow. */
    val needsSafetyFollowUp: Boolean get() = answers.getOrElse(8) { 0 } > 0

    val severity: String
        get() = when (total) {
            in 0..4 -> "Minimal"
            in 5..9 -> "Mild"
            in 10..14 -> "Moderate"
            in 15..19 -> "Moderately severe"
            else -> "Severe"
        }
}

/** Weekly check-in written by a partner/friend about the patient. */
data class PartnerCheckIn(
    val weekStart: LocalDate,
    val partnerName: String,
    val seemsMood: Int,
    val seemsEnergy: Int,
    val note: String = "",
)

enum class PartnerRole { Viewer, Nudger, Editor }
