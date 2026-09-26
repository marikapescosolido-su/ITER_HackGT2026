package com.iter.app.ui.patient.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ChoiceChip
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.navigation.Routes

@Composable
fun AccountScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    val repo = DemoRepository
    val p = repo.patient
    ScreenColumn(title = "Account", onBack = onBack) {
        SectionCard(title = p.name) {
            Text("Care team: ${p.clinicianName}, ${p.clinic}", style = MaterialTheme.typography.bodyMedium)
            Text("Monitoring: ${p.monitoredMedication.name} ${p.monitoredMedication.dose}", style = MaterialTheme.typography.bodyMedium)
        }
        SectionCard(title = "Daily reminder") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("9:00 am", "1:00 pm", "8:00 pm").forEach { time ->
                    ChoiceChip(time, repo.reminderTime == time, { repo.reminderTime = time })
                }
            }
            Text("Reminders never show how you're feeling on the lock screen.", style = MaterialTheme.typography.bodySmall)
        }
        TertiaryButton("Redo onboarding", { onOpen(Routes.ONBOARDING) })
    }
}
