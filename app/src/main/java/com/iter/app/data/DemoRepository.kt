package com.iter.app.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.Nudge
import com.iter.app.data.model.Phq9
import com.iter.app.data.model.ReportSettings
import com.iter.app.data.model.SharingSettings
import com.iter.app.data.model.SupportRole
import com.iter.app.data.model.Supporter
import com.iter.app.data.model.SupporterObservation
import com.iter.app.data.sample.SampleCheckIns
import com.iter.app.data.sample.SamplePatient
import com.iter.app.data.sample.SampleSupport
import java.time.LocalDate

/**
 * All app data, in memory. Nothing is saved: restarting the app resets to the sample patient.
 * Everything here is Compose state, so screens update automatically when it changes.
 */
object DemoRepository {
    var patient by mutableStateOf(SamplePatient.patient)
    var sharing by mutableStateOf(SharingSettings())
    var reportSettings by mutableStateOf(ReportSettings())

    val checkIns = mutableStateListOf<CheckIn>().apply { addAll(SampleCheckIns.checkIns()) }
    val phq9s = mutableStateListOf<Phq9>().apply { addAll(SampleCheckIns.phq9s()) }
    val supporters = mutableStateListOf<Supporter>().apply { addAll(SampleSupport.supporters) }
    val observations = mutableStateListOf<SupporterObservation>().apply { addAll(SampleSupport.observations()) }
    val nudges = mutableStateListOf<Nudge>()
    var reminderTime by mutableStateOf("8:00 pm")
    var remindLaterRequested by mutableStateOf(false)

    /** Set when answers or app use suggest a hard moment; the patient home shows the Concern Alert overlay. */
    var concernPending by mutableStateOf(false)
    var concernDismissedToday by mutableStateOf(false)
    val clinicianNotes = mutableStateListOf<com.iter.app.data.model.ClinicianNote>()

    val today: LocalDate get() = LocalDate.now()
    val todaysCheckIn: CheckIn? get() = checkIns.firstOrNull { it.date == today }

    fun saveCheckIn(checkIn: CheckIn) {
        checkIns.removeAll { it.date == checkIn.date }
        checkIns.add(checkIn)
        checkIns.sortBy { it.date }
    }

    fun savePhq9(phq9: Phq9) {
        phq9s.removeAll { it.date == phq9.date }
        phq9s.add(phq9)
        phq9s.sortBy { it.date }
    }

    fun sendNudge(nudge: Nudge) = nudges.add(0, nudge)

    fun saveObservation(observation: SupporterObservation) = observations.add(observation)

    fun setRole(name: String, role: SupportRole) {
        val i = supporters.indexOfFirst { it.name == name }
        if (i >= 0) supporters[i] = supporters[i].copy(role = role)
    }

    fun removeSupporter(name: String) = supporters.removeAll { it.name == name }

    fun addSupporter(supporter: Supporter) {
        supporters.removeAll { it.name == supporter.name }
        supporters.add(supporter)
    }
}
