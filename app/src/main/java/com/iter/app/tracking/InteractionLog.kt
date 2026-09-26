package com.iter.app.tracking

import androidx.compose.runtime.mutableStateListOf
import com.iter.app.data.DemoRepository
import java.time.LocalDateTime

/**
 * Interaction signals (README: "Tracking interaction patterns"). Only recorded when the patient
 * turned on interaction tracking in Sharing & privacy. In memory only.
 *
 * TODO(team): flag unusual patterns, e.g. many app opens in a short time, and offer the optional
 * voice check-in (see voice/VoiceCheckIn.kt). A pattern is context, never a diagnosis.
 */
object InteractionLog {
    enum class Event { AppOpened, CheckInStarted, CheckInCompleted, SupportScreenViewed }

    data class Entry(val event: Event, val at: LocalDateTime)

    val entries = mutableStateListOf<Entry>()

    fun record(event: Event) {
        if (DemoRepository.sharing.interactionTracking) entries.add(Entry(event, LocalDateTime.now()))
    }

    fun opensToday(): Int = entries.count { it.event == Event.AppOpened && it.at.toLocalDate() == DemoRepository.today }
}
