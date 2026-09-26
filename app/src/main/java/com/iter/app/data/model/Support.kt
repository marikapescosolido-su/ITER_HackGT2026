package com.iter.app.data.model

import java.time.LocalDate
import java.time.LocalDateTime

enum class SupportRole(val label: String, val description: String) {
    Viewer("Viewer", "Sees only what the patient chooses to share. Can't change anything."),
    Nudger("Nudger", "Can also send a kind reminder when a check-in is missing."),
    Editor("Editor", "Can also suggest corrections. Edits are labeled and need the patient's approval."),
}

data class Supporter(
    val name: String,
    val relation: String,
    val role: SupportRole,
    val challengeEnabled: Boolean = false,
)

data class Nudge(
    val from: String,
    val message: String,
    val sentAt: LocalDateTime,
)

/** Weekly survey a supporter fills in from their own point of view. 0..10, higher = better. */
data class SupporterObservation(
    val date: LocalDate,
    val supporterName: String,
    val mood: Int,
    val energy: Int,
    val sleep: Int,
    val social: Int,
    val note: String = "",
)

/** A note the clinician adds from the Medication context screen. Shown in the report as a clinician note. */
data class ClinicianNote(
    val date: LocalDate,
    val text: String,
)
