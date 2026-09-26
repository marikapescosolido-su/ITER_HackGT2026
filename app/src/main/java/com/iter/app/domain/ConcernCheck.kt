package com.iter.app.domain

import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.Question

/**
 * Decides when to gently offer the Concern Alert overlay. Demo thresholds, not clinically validated:
 * a real version needs clinician-reviewed rules. It only ever offers support; it never diagnoses.
 */
object ConcernCheck {
    private const val HIGH_ANXIETY = 8
    private const val LOW_MOOD = 2
    private const val MANY_OPENS_TODAY = 8

    fun fromCheckIn(checkIn: CheckIn): Boolean {
        val anxiety = checkIn.score(Question.Anxiety) ?: 0
        val mood = checkIn.score(Question.Mood) ?: 10
        return anxiety >= HIGH_ANXIETY || mood <= LOW_MOOD
    }

    /** Only used when the patient turned on interaction tracking. */
    fun fromAppOpens(opensToday: Int): Boolean = opensToday >= MANY_OPENS_TODAY
}
