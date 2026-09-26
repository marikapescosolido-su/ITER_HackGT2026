package com.iter.app.domain

import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.Question
import java.time.LocalDate

/**
 * What a supporter may see: a general level only, never scores or charts.
 * Compares the patient's mood this week with the week before.
 */
object SupporterSummary {
    enum class Level { Lower, Same, Brighter, NotEnoughData }

    fun level(checkIns: List<CheckIn>, today: LocalDate = LocalDate.now()): Level {
        fun avg(from: LocalDate, to: LocalDate) = checkIns
            .filter { !it.date.isBefore(from) && !it.date.isAfter(to) }
            .mapNotNull { it.score(Question.Mood) }
            .takeIf { it.size >= 3 }
            ?.average()
        val thisWeek = avg(today.minusDays(6), today) ?: return Level.NotEnoughData
        val lastWeek = avg(today.minusDays(13), today.minusDays(7)) ?: return Level.NotEnoughData
        return when {
            thisWeek <= lastWeek - 1 -> Level.Lower
            thisWeek >= lastWeek + 1 -> Level.Brighter
            else -> Level.Same
        }
    }

    fun sentence(name: String, level: Level): String = when (level) {
        Level.Lower -> "$name has been feeling lower than usual this week. A kind message may help."
        Level.Same -> "$name has been feeling about the same as last week."
        Level.Brighter -> "$name has been feeling a bit brighter this week."
        Level.NotEnoughData -> "Not enough check-ins yet this week to share how $name is doing."
    }
}
