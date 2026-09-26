package com.iter.app.ui.patient.checkin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.domain.Streak
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.buttons.ButtonSize
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.StreakChip

@Composable
fun CheckInDoneScreen(onDone: () -> Unit) {
    val repo = DemoRepository
    val messages = SupportMessages.afterCheckIn(repo.patient.clinicianName)
    val streak = Streak.compute(repo.checkIns)

    ScreenColumn(title = "Check-in saved") {
        messages.forEach { Text(it, style = MaterialTheme.typography.bodyLarge) }
        StreakChip("${streak.days}-day streak")
        Text(streak.message, style = MaterialTheme.typography.bodyMedium)
        PrimaryButton("Done", onDone, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}
