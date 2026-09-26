package com.iter.app.domain

import com.iter.app.data.model.DoseRecord
import com.iter.app.data.model.Medication
import com.iter.app.data.model.Patient
import java.time.LocalDate

/** A medication that will run out soon. Shown to the clinician in the report (and PDF). */
data class RefillAlert(
    val medication: Medication,
    val daysLeft: Int,
    val runsOutOn: LocalDate,
)

/** Estimates how many days of pills are left, assuming one dose a day. */
object MedicationSupply {
    /** At or below this many days left, the report flags the medication. */
    const val LOW_DAYS = 7

    /** Pills left today: the last count minus every dose logged as taken after the count. Null if never counted. */
    fun pillsLeft(medication: Medication, doseLog: List<DoseRecord>, today: LocalDate): Int? {
        val supply = medication.supply ?: return null
        val takenSinceCount = doseLog.count {
            it.medication == medication.name && it.taken && it.date.isAfter(supply.countedOn) && !it.date.isAfter(today)
        }
        return (supply.pillsOnHand - takenSinceCount * supply.pillsPerDose).coerceAtLeast(0)
    }

    fun daysLeft(medication: Medication, doseLog: List<DoseRecord>, today: LocalDate): Int? {
        val pills = pillsLeft(medication, doseLog, today) ?: return null
        return pills / (medication.supply?.pillsPerDose ?: 1)
    }

    fun isLow(daysLeft: Int?): Boolean = daysLeft != null && daysLeft <= LOW_DAYS

    fun refillAlerts(patient: Patient, doseLog: List<DoseRecord>, today: LocalDate): List<RefillAlert> =
        patient.medications.mapNotNull { med ->
            val days = daysLeft(med, doseLog, today)
            if (days != null && isLow(days)) RefillAlert(med, days, today.plusDays(days.toLong())) else null
        }.sortedBy { it.daysLeft }
}
