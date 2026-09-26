package com.iter.app.domain

import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.Question

/**
 * Decides when to gently offer the Concern Alert overlay. Demo thresholds, not clinically validated:
 * a real version needs clinician-reviewed rules. It only ever offers support; it never diagnoses.
 */
object ConcernCheck {
    private const val HIGH = 8f
    private const val MANY_OPENS_TODAY = 8

    fun fromCheckIn(checkIn: CheckIn): Boolean {
        // Higher = more noticeable on every question, so "low mood" is a high score.
        return listOf(Question.Mood, Question.Tension).any { (checkIn.score(it) ?: 0f) >= HIGH }
    }

    /** Only used when the patient turned on interaction tracking. */
    fun fromAppOpens(opensToday: Int): Boolean = opensToday >= MANY_OPENS_TODAY
}
