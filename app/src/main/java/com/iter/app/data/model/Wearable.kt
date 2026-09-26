package com.iter.app.data.model

/** The smartwatch the patient connected and its latest readings. Demo data: nothing is read from a real watch yet. */
data class Wearable(
    val deviceName: String,
    val lastSynced: String,
    val readings: List<WearableReading>,
    val sleep: SleepStages,
)

/** One measurement as the watch reports it. [detail] says when or how it was taken. */
data class WearableReading(val label: String, val value: String, val detail: String)

/** Last night's sleep, in minutes per stage. */
data class SleepStages(val awake: Int, val rem: Int, val light: Int, val deep: Int) {
    val asleep: Int get() = rem + light + deep
}
