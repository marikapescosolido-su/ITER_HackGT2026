package com.iter.app.data.sample

import com.iter.app.data.model.SupportRole
import com.iter.app.data.model.Supporter
import com.iter.app.data.model.SupporterObservation

object SampleSupport {
    val supporters = listOf(
        Supporter("Sam", "Partner", SupportRole.Nudger, challengeEnabled = true),
        Supporter("Jordan", "Friend", SupportRole.Viewer),
    )

    fun observations(): List<SupporterObservation> = (0 until 6).map { week ->
        SupporterObservation(
            date = SamplePatient.start.plusWeeks(week.toLong()).plusDays(6),
            supporterName = "Sam",
            mood = (2 + week).coerceAtMost(10),
            energy = if (week in 1..3) 3 else 5,
            sleep = 4 + week / 2,
            social = 2 + week,
            note = when (week) {
                3 -> "Seems more like themselves, but tired all the time."
                5 -> "Laughing more. Went out with friends on Saturday."
                else -> ""
            },
        )
    }
}
