package com.iter.app.data.sample

import com.iter.app.data.model.Baseline
import com.iter.app.data.model.DoseChange
import com.iter.app.data.model.Medication
import com.iter.app.data.model.Patient
import com.iter.app.data.model.PillSupply
import java.time.LocalDate

/** Demo patient: 6 weeks into sertraline, dose raised at day 21. */
object SamplePatient {
    const val TOTAL_DAYS = 42
    const val DOSE_CHANGE_DAY = 21

    val start: LocalDate = LocalDate.now().minusDays(TOTAL_DAYS.toLong())

    val patient = Patient(
        name = "Alex",
        age = 29,
        condition = "Depression",
        clinicianName = "Dr. Rivera",
        clinic = "Midtown Health Clinic",
        treatmentStart = start,
        medications = listOf(
            // Pill counts chosen so sertraline is close to running out (the report's refill signal).
            Medication(
                "Sertraline", "100 mg", start, "Depression", isMonitored = true,
                supply = PillSupply(pillsOnHand = 24, countedOn = start.plusDays(TOTAL_DAYS - 25L)),
            ),
            Medication(
                "Cetirizine", "10 mg", start.minusYears(2), "Seasonal allergies",
                supply = PillSupply(pillsOnHand = 60, countedOn = start.plusDays(TOTAL_DAYS - 10L)),
            ),
            Medication(
                "Vitamin D", "1000 IU", start.minusMonths(6), "Supplement",
                supply = PillSupply(pillsOnHand = 90, countedOn = start.plusDays(TOTAL_DAYS - 30L)),
            ),
        ),
        doseChanges = listOf(
            DoseChange(start.plusDays(DOSE_CHANGE_DAY.toLong()), "Sertraline", "50 mg", "100 mg"),
        ),
        baseline = Baseline(
            typicalSleepHours = 7f,
            typicalMood = 6,
            previousMedications = listOf("Fluoxetine (2023): stopped after 8 weeks, felt no change"),
            otherConditions = listOf("Seasonal allergies"),
            familyHistory = listOf("Parent: depression", "Grandparent: type 2 diabetes"),
            supportSystem = "Partner (Sam), one close friend",
        ),
    )
}
