package com.iter.app.domain

/** How often the clinician gets the PDF. Chosen together by clinician and patient. */
object ReportSchedule {
    val options = listOf(3, 7, 14)

    fun label(days: Int): String = when (days) {
        7 -> "every week"
        14 -> "every two weeks"
        else -> "every $days days"
    }

    fun chipLabel(days: Int): String = when (days) {
        7 -> "1 week"
        14 -> "2 weeks"
        else -> "$days days"
    }
}
