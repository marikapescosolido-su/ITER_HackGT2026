package com.iter.app.data.model

import java.time.LocalDate

data class Patient(
    val name: String,
    val age: Int,
    val condition: String,
    val clinicianName: String,
    val clinic: String,
    val treatmentStart: LocalDate,
    val medications: List<Medication>,
    val doseChanges: List<DoseChange>,
    val baseline: Baseline,
) {
    /** The medication ITER is monitoring (the depression treatment). */
    val monitoredMedication: Medication get() = medications.first { it.isMonitored }
}

data class Medication(
    val name: String,
    val dose: String,
    val since: LocalDate,
    val purpose: String,
    val isMonitored: Boolean = false,
)

data class DoseChange(
    val date: LocalDate,
    val medication: String,
    val from: String,
    val to: String,
)

/** Collected once at onboarding; gives context for interpreting daily answers. */
data class Baseline(
    val typicalSleepHours: Float,
    val typicalMood: Int,
    val previousMedications: List<String>,
    val otherConditions: List<String>,
    val familyHistory: List<String>,
    val supportSystem: String,
)
