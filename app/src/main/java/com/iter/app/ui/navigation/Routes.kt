package com.iter.app.ui.navigation

/** Every screen's route. Add new routes here, then register them in that area's *Navigation.kt. */
object Routes {
    const val DEMO = "demo"

    // Patient (Alex's phone)
    const val ONBOARDING = "patient/onboarding"
    const val PATIENT_HOME = "patient/home"
    const val CHECK_IN = "patient/check_in"
    const val CHECK_IN_DONE = "patient/check_in_done"
    const val PHQ9 = "patient/phq9"
    const val SAFETY = "patient/safety"
    const val SHARING = "patient/sharing"

    // Supporter (Sam's phone)
    const val SUPPORTER_HOME = "supporter/home"
    const val NUDGE = "supporter/nudge"
    const val SUPPORTER_SURVEY = "supporter/survey"

    // Clinician (the weekly report / PDF)
    const val REPORT = "clinician/report"
    const val REPORT_SETTINGS = "clinician/report_settings"
}
