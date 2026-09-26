package com.iter.app.ui.patient.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.SupportRole
import com.iter.app.data.model.Supporter

/** Answers collected across the onboarding steps; written to DemoRepository at the end. */
class OnboardingState {
    private val patient = DemoRepository.patient

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    val familyConditions = mutableStateListOf<String>()
    var familyNotes by mutableStateOf("")
    var medication by mutableStateOf(patient.monitoredMedication.name)
    var dose by mutableStateOf(patient.monitoredMedication.dose)
    var otherMedications by mutableStateOf(patient.medications.filterNot { it.isMonitored }.joinToString { "${it.name} ${it.dose}" })
    var inviteName by mutableStateOf("")
    var inviteRelation by mutableStateOf("")
    var inviteContact by mutableStateOf("")

    val canCreateAccount get() = name.isNotBlank() && email.contains("@")
    val familyAnswered get() = familyConditions.isNotEmpty()
    val medicationAnswered get() = medication.isNotBlank() && dose.isNotBlank()
    val canInvite get() = inviteName.isNotBlank() && inviteContact.isNotBlank()

    fun save(invite: Boolean) {
        val meds = patient.medications.map {
            if (it.isMonitored) it.copy(name = medication.trim(), dose = dose.trim()) else it
        }
        val history = familyConditions.filterNot { it == NONE_KNOWN } +
            listOfNotNull(familyNotes.trim().takeIf { it.isNotEmpty() })
        DemoRepository.patient = patient.copy(
            name = name.trim().ifEmpty { patient.name },
            medications = meds,
            baseline = patient.baseline.copy(familyHistory = history.ifEmpty { listOf("None known") }),
        )
        if (invite && canInvite) {
            DemoRepository.addSupporter(
                Supporter(inviteName.trim(), inviteRelation.trim().ifEmpty { "Supporter" }, SupportRole.Viewer),
            )
        }
    }

    companion object {
        const val NONE_KNOWN = "None that I know of"
        val conditions = listOf(
            "Depression", "Anxiety", "Bipolar disorder", "Substance use",
            "Heart disease", "Diabetes", NONE_KNOWN,
        )
    }
}
