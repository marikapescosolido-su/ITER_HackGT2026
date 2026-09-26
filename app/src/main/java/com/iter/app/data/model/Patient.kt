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
    /** Pills at home, if the patient has counted them. Null = unknown, so no refill estimate. */
    val supply: PillSupply? = null,
)

/** A pill count on a given day; doses logged after that day are subtracted to estimate what's left. */
data class PillSupply(
    val pillsOnHand: Int,
    val countedOn: LocalDate,
    val pillsPerDose: Int = 1,
)

/** Whether one medication was taken on one day. Days without a record are unknown, not missed. */
data class DoseRecord(
    val date: LocalDate,
    val medication: String,
    val taken: Boolean,
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
