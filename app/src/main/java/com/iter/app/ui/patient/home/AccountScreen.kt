package com.iter.app.ui.patient.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.domain.ReportSchedule
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ChoiceChip
import com.iter.app.ui.components.buttons.SecondaryButton
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.navigation.Routes
import com.iter.app.ui.theme.IterTheme
import java.time.format.DateTimeFormatter

@Composable
fun AccountScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    val repo = DemoRepository
    val p = repo.patient
    var customDays by rememberSaveable { mutableStateOf(repo.reportSettings.intervalDays.toString()) }
    val customInterval = customDays.toIntOrNull()?.takeIf { it > 0 }
    // UI only for now: Apply saves the schedule and confirms it. Nothing is emailed yet.
    var applied by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    ScreenColumn(title = "Account", onBack = onBack) {
        SectionCard(title = "Your care team") {
            Text(p.clinicianName, style = MaterialTheme.typography.titleMedium)
            Text(p.clinic, style = MaterialTheme.typography.bodyMedium)
            Text("Monitoring: ${p.monitoredMedication.name} ${p.monitoredMedication.dose}", style = MaterialTheme.typography.bodyMedium)
            Text(
                "Choose how often your updated survey report (PDF/Excel) is sent to ${p.clinicianName}.",
                style = MaterialTheme.typography.bodySmall,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ReportSchedule.options.forEach { days ->
                    ChoiceChip(
                        text = ReportSchedule.chipLabel(days),
                        selected = repo.reportSettings.intervalDays == days,
                        onClick = {
                            repo.reportSettings = repo.reportSettings.copy(intervalDays = days)
                            customDays = days.toString()
                            applied = false
                        },
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = customDays,
                    onValueChange = { input ->
                        customDays = input.filter(Char::isDigit)
                        applied = false
                    },
                    label = { Text("Custom number of days") },
                    supportingText = {
                        if (customDays.isNotEmpty() && customInterval == null) Text("Enter at least 1 day")
                    },
                    isError = customDays.isNotEmpty() && customInterval == null,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                )
                SecondaryButton(
                    text = "Apply",
                    onClick = {
                        customInterval?.let { days ->
                            repo.reportSettings = repo.reportSettings.copy(intervalDays = days)
                            applied = true
                            focusManager.clearFocus()
                        }
                    },
                    enabled = customInterval != null,
                )
            }
            if (applied) {
                val next = repo.today.plusDays(repo.reportSettings.intervalDays.toLong())
                Text(
                    "Saved. Your next report goes to ${p.clinicianName} on ${next.format(DateTimeFormatter.ofPattern("EEEE, MMM d"))}.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IterTheme.chrome.brandText,
                )
            }
            Text(
                "Current schedule: ${ReportSchedule.label(repo.reportSettings.intervalDays)}. Reports are sent automatically by email.",
                style = MaterialTheme.typography.bodySmall,
            )
        }
        MedicationsCard()
        MedicationCalendarCard()
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
