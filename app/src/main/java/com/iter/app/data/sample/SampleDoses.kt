package com.iter.app.data.sample

import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.DoseRecord
import kotlin.random.Random

/**
 * Dose history for the demo calendar. The monitored medication comes from the daily check-ins
 * ("did you take it?"); the other medications get a believable, mostly-taken history.
 */
object SampleDoses {
    fun doseLog(checkIns: List<CheckIn>): List<DoseRecord> {
        val patient = SamplePatient.patient
        val monitored = patient.monitoredMedication.name
        val rng = Random(11)
        val fromCheckIns = checkIns.map { DoseRecord(it.date, monitored, it.tookMedication) }
        val others = patient.medications.filterNot { it.isMonitored }.flatMap { med ->
            (0 until SamplePatient.TOTAL_DAYS).map { day ->
                DoseRecord(SamplePatient.start.plusDays(day.toLong()), med.name, rng.nextFloat() > 0.08f)
            }
        }
        return (fromCheckIns + others).sortedBy { it.date }
    }
}
