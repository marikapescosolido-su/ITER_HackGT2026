package com.iter.app.data.sample

import com.iter.app.data.model.SleepStages
import com.iter.app.data.model.Wearable
import com.iter.app.data.model.WearableReading

/** Demo patient's watch: typical, steady readings for a healthy adult. */
object SampleWearable {
    val watch = Wearable(
        deviceName = "Samsung Galaxy Watch7",
        lastSynced = "4 min ago",
        readings = listOf(
            WearableReading("Heart rate", "68 bpm", "Resting"),
            WearableReading("Heart rate variability", "42 ms", "Overnight average"),
            WearableReading("Blood oxygen", "97%", "Last night"),
            WearableReading("Electrocardiogram", "Normal", "Sinus rhythm, Tuesday"),
            WearableReading("Skin temperature", "+0.4 °F", "Vs. your usual, overnight"),
            WearableReading("Blood pressure", "118/76", "mmHg, this morning"),
            WearableReading("Respiratory rate", "15", "Breaths a minute, asleep"),
            WearableReading("Workout intensity", "Moderate", "32 min brisk walk today"),
        ),
        sleep = SleepStages(awake = 18, rem = 105, light = 245, deep = 80),
    )
}
