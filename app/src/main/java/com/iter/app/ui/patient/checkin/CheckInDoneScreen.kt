package com.iter.app.ui.patient.checkin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.domain.Lantern
import com.iter.app.domain.Streak
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.buttons.ButtonSize
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.StreakChip
import com.iter.app.ui.components.illustration.BearPath

@Composable
fun CheckInDoneScreen(onDone: () -> Unit) {
    val repo = DemoRepository
    val messages = SupportMessages.afterCheckIn(repo.patient.clinicianName)
    val streak = Streak.compute(repo.checkIns)
    val lantern = Lantern.compute(repo.checkIns)
    val before = Lantern.compute(repo.checkIns.filter { it.date != repo.today })

    ScreenColumn(title = "Check-in saved") {
        BearPath(lantern.level, fromLevel = before.level)
        Text(lantern.message, style = MaterialTheme.typography.bodyLarge)
        messages.forEach { Text(it, style = MaterialTheme.typography.bodyLarge) }
        StreakChip("${streak.days}-day streak")
        PrimaryButton("Done", onDone, Modifier.fillMaxWidth(), size = ButtonSize.Large)
    }
}
