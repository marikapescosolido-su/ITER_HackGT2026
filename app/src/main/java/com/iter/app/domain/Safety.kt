package com.iter.app.domain

import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.Question

/**
 * Crisis resources shown when answers suggest a safety concern.
 * ITER is not an emergency service; this must say so and point to real people.
 * US numbers for the demo; a real version must use the patient's location.
 */
object Safety {
    data class Resource(val title: String, val detail: String, val phone: String?)

    val resources = listOf(
        Resource("988 Suicide & Crisis Lifeline", "Call or text 988, any time, free and confidential.", "988"),
        Resource("Emergency services", "If you are in immediate danger, call 911.", "911"),
        Resource("Your care team", "Contact your clinician or clinic as soon as you can.", null),
    )

    const val DISCLAIMER =
        "ITER is not monitored in real time and is not an emergency service. Your clinician will see this answer in your next report."

    /**
     * Demo threshold, not clinically validated. The slider must never be the only safety assessment:
     * a real version replaces this with clinician-approved follow-up questions (e.g. the NIMH ASQ toolkit).
     */
    const val IMMEDIATE_DANGER = 9f

    /** Any self-harm answer above 0, or no answer at all (skipped counts too), needs the safety pathway. */
    fun needsFollowUp(checkIn: CheckIn): Boolean = (checkIn.score(Question.SelfHarm) ?: 1f) > 0f
}
