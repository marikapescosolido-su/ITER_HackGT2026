package com.iter.app.domain

import com.iter.app.data.model.NotificationDetail

/**
 * All supportive wording in one place so it can be reviewed together.
 * Rules from the README: no false positivity, no guilt, never "you are improving" from one answer.
 */
object SupportMessages {

    fun greeting(name: String, hour: Int): String {
        val part = when (hour) {
            in 5..11 -> "Good morning"
            in 12..17 -> "Good afternoon"
            else -> "Good evening"
        }
        return "$part, $name."
    }

    const val CHECK_IN_INTRO = "Today's check-in takes about a minute. There are no right answers."

    /** No scores or comparisons: only the clinician sees the data. */
    fun afterCheckIn(clinicianName: String): List<String> = listOf(
        "Thank you for checking in today.",
        "Your answers go to $clinicianName with your next report. Only your care team sees them.",
    )

    /** What a viewer sees about the patient (only if the patient allowed it). */
    fun supporterUpdate(patientName: String, lastMood: Int?, detail: NotificationDetail): String? {
        if (lastMood == null || lastMood > 4) return null
        return when (detail) {
            NotificationDetail.WithName ->
                "Hi, yesterday $patientName felt a bit down. Remember to be kind and check in when you can."
            NotificationDetail.General ->
                "Someone you support had a more difficult day yesterday. A small message may be appreciated."
        }
    }

    val nudgeTemplates = listOf(
        "You've got this. Your check-in will only take a minute.",
        "I'm thinking of you today. Check in when you feel ready.",
        "Just a little reminder: I'm here for you.",
        "Take your time. Today's check-in helps your care team understand how you're feeling.",
    )

    fun reminder(hadHardDayYesterday: Boolean): String =
        if (hadHardDayYesterday) "Whenever you're ready, your check-in is here." else "Time for today's one-minute check-in."
}
