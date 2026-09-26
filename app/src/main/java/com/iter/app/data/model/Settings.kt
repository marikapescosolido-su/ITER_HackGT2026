package com.iter.app.data.model

enum class NotificationDetail(val label: String) {
    General("General (\"had a harder day\")"),
    WithName("Include my name"),
}

/** What the patient allows to be shared. Patient-controlled; see the Sharing & privacy screen. */
data class SharingSettings(
    val shareWithClinician: Boolean = true,
    val includeSupporterObservations: Boolean = true,
    val supporterMoodNotifications: Boolean = true,
    val notificationDetail: NotificationDetail = NotificationDetail.WithName,
    val interactionTracking: Boolean = false,
    val researchConsent: Boolean = false,
)

/** Set by the clinician: how often a report is made and how many days it covers. */
data class ReportSettings(
    val intervalDays: Int = 7,
    val windowDays: Int = 7,
    val doctorEmail: String = "dr.rivera@clinic.example",
    val includeRawAnswers: Boolean = true,
)
