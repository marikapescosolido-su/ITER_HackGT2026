package com.iter.app.ui.patient.checkin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.domain.Streak
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard

@Composable
fun CheckInDoneScreen(onDone: () -> Unit) {
    val repo = DemoRepository
    val today = repo.todaysCheckIn
    val yesterday = repo.checkIns.lastOrNull { it.date == repo.today.minusDays(1) }
    val messages = if (today != null) {
        SupportMessages.afterCheckIn(today, yesterday, repo.patient.clinicianName)
    } else {
        emptyList()
    }
    val streak = Streak.compute(repo.checkIns)

    ScreenColumn(title = "Check-in saved") {
        messages.forEach { Text(it, style = MaterialTheme.typography.bodyLarge) }
        SectionCard(title = "Streak") {
            Text("${streak.days} days", style = MaterialTheme.typography.headlineSmall)
            Text(streak.message)
        }
        Button(onClick = onDone, modifier = Modifier.fillMaxWidth()) { Text("Done") }
    }
}
