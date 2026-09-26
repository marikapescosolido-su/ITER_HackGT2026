package com.iter.app.domain

import com.iter.app.data.model.DoseRecord
import com.iter.app.data.model.Medication
import com.iter.app.data.model.PillSupply
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MedicationSupplyTest {
    private val counted = LocalDate.of(2026, 9, 1)
    private val today = LocalDate.of(2026, 9, 11)

    private fun med(pills: Int, perDose: Int = 1) =
        Medication("Sertraline", "100 mg", counted, "Depression", supply = PillSupply(pills, counted, perDose))

    private fun taken(vararg daysAfterCount: Int, taken: Boolean = true) =
        daysAfterCount.map { DoseRecord(counted.plusDays(it.toLong()), "Sertraline", taken) }

    @Test fun `subtracts only doses taken after the count`() {
        val log = taken(0, 1, 2, 3) + taken(4, taken = false)
        assertEquals(27, MedicationSupply.daysLeft(med(30), log, today)) // day 0 is the count day itself
    }

    @Test fun `ignores other medications and future records`() {
        val log = taken(1, 2) + DoseRecord(counted.plusDays(3), "Vitamin D", true) + taken(20)
        assertEquals(8, MedicationSupply.daysLeft(med(10), log, today))
    }

    @Test fun `two pills per dose halves the days`() {
        assertEquals(5, MedicationSupply.daysLeft(med(10, perDose = 2), emptyList(), today))
    }

    @Test fun `never goes below zero`() {
        assertEquals(0, MedicationSupply.daysLeft(med(2), taken(1, 2, 3, 4), today))
    }

    @Test fun `no pill count means no estimate and no alert`() {
        val noCount = Medication("Vitamin D", "1000 IU", counted, "Supplement")
        assertNull(MedicationSupply.daysLeft(noCount, emptyList(), today))
    }

    @Test fun `alerts at seven days or fewer`() {
        val patient = com.iter.app.data.sample.SamplePatient.patient.copy(medications = listOf(med(8), med(7).copy(name = "Low")))
        val alerts = MedicationSupply.refillAlerts(patient, emptyList(), today)
        assertEquals(listOf("Low"), alerts.map { it.medication.name })
        assertEquals(today.plusDays(7), alerts.single().runsOutOn)
        assertTrue(MedicationSupply.isLow(0))
    }

    @Test fun `demo report flags sertraline as running out`() {
        val checkIns = com.iter.app.data.sample.SampleCheckIns.checkIns()
        val report = ReportBuilder.build(
            patient = com.iter.app.data.sample.SamplePatient.patient,
            checkIns = checkIns,
            phq9s = emptyList(),
            observations = emptyList(),
            windowDays = 7,
            includeSupporter = false,
            doseLog = com.iter.app.data.sample.SampleDoses.doseLog(checkIns),
        )
        assertEquals(listOf("Sertraline"), report.refillAlerts.map { it.medication.name })
    }
}
