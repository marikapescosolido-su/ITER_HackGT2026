package com.iter.app.data.model

/**
 * A 0-10 slider question in the daily check-in.
 * Core questions are asked every day so days stay comparable; the rest rotate (see DailyQuestionPlan).
 */
enum class Question(
    val label: String,
    val prompt: String,
    val lowLabel: String,
    val highLabel: String,
    val higherIsBetter: Boolean,
    val isCore: Boolean,
) {
    Mood("Mood", "How would you describe your mood today?", "Very low", "Very good", true, true),
    Anxiety("Anxiety", "How anxious or on edge have you felt?", "Calm", "Very anxious", false, true),
    Energy("Energy", "How was your energy and motivation?", "None", "Plenty", true, true),
    SleepQuality("Sleep quality", "How well did you sleep last night?", "Poorly", "Very well", true, true),
    Concentration("Concentration", "How easy was it to focus?", "Very hard", "Easy", true, false),
    Appetite("Appetite", "How was your appetite?", "Much less", "Usual", true, false),
    Stress("Stress", "How stressful did today feel?", "Not at all", "Very", false, false),
    Connection("Connection", "How connected to other people did you feel?", "Alone", "Connected", true, false),
    DailyTasks("Daily tasks", "How manageable were everyday tasks?", "Very hard", "Manageable", true, false),
    Physical("Physical well-being", "How did your body feel today?", "Unwell", "Well", true, false),
}
