package com.iter.app.domain

import com.iter.app.data.model.CheckIn
import java.time.LocalDate

/**
 * Check-in streak with a one-day grace period, so a single missed day doesn't reset progress
 * (README: the streak "must not punish or shame someone for missing a day").
 */
data class StreakInfo(
    val days: Int,
    val checkedInToday: Boolean,
    val usedGraceDay: Boolean,
) {
    val message: String
        get() = when {
            days == 0 -> "You can start again today."
            checkedInToday -> "Thank you for keeping going."
            else -> "Today's check-in keeps it going."
        }
}

object Streak {
    fun compute(checkIns: List<CheckIn>, today: LocalDate = LocalDate.now()): StreakInfo {
        val dates = checkIns.map { it.date }.toSet()
        val checkedInToday = today in dates
        var day = if (checkedInToday) today else today.minusDays(1)
        var count = 0
        var graceUsed = false
        while (true) {
            when {
                day in dates -> count++
                !graceUsed && day.minusDays(1) in dates && count > 0 -> graceUsed = true
                else -> break
            }
            day = day.minusDays(1)
        }
        return StreakInfo(count, checkedInToday, graceUsed)
    }
}
