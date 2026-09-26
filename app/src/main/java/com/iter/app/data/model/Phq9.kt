package com.iter.app.data.model

import java.time.LocalDate

/** Weekly PHQ-9 (a validated depression questionnaire). Each answer is 0..3; total is 0..27. */
data class Phq9(
    val date: LocalDate,
    val answers: List<Int>,
) {
    val total: Int get() = answers.sum()

    /** Item 9 asks about thoughts of self-harm; any answer above 0 starts the safety flow. */
    val needsSafetyFollowUp: Boolean get() = answers.getOrElse(8) { 0 } > 0

    val severity: String
        get() = when (total) {
            in 0..4 -> "Minimal"
            in 5..9 -> "Mild"
            in 10..14 -> "Moderate"
            in 15..19 -> "Moderately severe"
            else -> "Severe"
        }

    companion object {
        val questions = listOf(
            "Little interest or pleasure in doing things",
            "Feeling down, depressed, or hopeless",
            "Trouble falling or staying asleep, or sleeping too much",
            "Feeling tired or having little energy",
            "Poor appetite or overeating",
            "Feeling bad about yourself, or that you are a failure or have let yourself or your family down",
            "Trouble concentrating on things, such as reading or watching television",
            "Moving or speaking so slowly that other people could have noticed, or the opposite: being so fidgety or restless that you have been moving around a lot more than usual",
            "Thoughts that you would be better off dead, or of hurting yourself in some way",
        )
        val options = listOf("Not at all", "Several days", "More than half the days", "Nearly every day")
    }
}
