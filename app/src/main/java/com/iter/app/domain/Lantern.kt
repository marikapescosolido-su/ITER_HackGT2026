package com.iter.app.domain

import com.iter.app.data.model.CheckIn
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * The lantern shows this week's check-in progress (Monday to Sunday).
 * Each check-in makes it one step brighter; a missed day dims it one step.
 * It never goes out: level 0 is still a small ember. A new week starts again from the ember.
 */
object Lantern {
    const val MAX = 7

    data class State(val level: Int, val checkedInToday: Boolean) {
        /** 0f (ember) .. 1f (brightest). */
        val brightness: Float get() = level / MAX.toFloat()

        val message: String
            get() = when {
                level >= MAX -> "Your lantern is shining its brightest. What a week."
                checkedInToday -> "Your lantern grew a little brighter today."
                level == 0 -> "Your lantern is still glowing. Today's check-in will brighten it."
                else -> "Today's check-in will make your lantern brighter."
            }
    }

    fun compute(checkIns: List<CheckIn>, today: LocalDate = LocalDate.now()): State {
        val dates = checkIns.map { it.date }.toSet()
        var day = today.with(DayOfWeek.MONDAY)
        var level = 0
        while (!day.isAfter(today)) {
            level = when {
                day in dates -> (level + 1).coerceAtMost(MAX)
                day.isBefore(today) -> (level - 1).coerceAtLeast(0) // missed: dim one step, never out
                else -> level // today, not done yet
            }
            day = day.plusDays(1)
        }
        return State(level, today in dates)
    }
}
