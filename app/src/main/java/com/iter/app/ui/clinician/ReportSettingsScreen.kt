package com.iter.app.ui.clinician

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard

/** Clinician chooses how often reports are sent and how many days they cover. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportSettingsScreen(onBack: () -> Unit) {
    val repo = DemoRepository
    val s = repo.reportSettings

    ScreenColumn(title = "Report settings", subtitle = "Set by the clinician. The patient can see when reports are sent and to whom.", onBack = onBack) {
        SectionCard(title = "Send a report every") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(7, 14, 28).forEach { days ->
                    FilterChip(selected = s.intervalDays == days, onClick = { repo.reportSettings = s.copy(intervalDays = days) }, label = { Text("$days days") })
                }
            }
        }
        SectionCard(title = "Each report covers") {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(7, 14, 28).forEach { days ->
                    FilterChip(selected = s.windowDays == days, onClick = { repo.reportSettings = s.copy(windowDays = days) }, label = { Text("$days days") })
                }
            }
        }
        SectionCard(title = "Delivery") {
            OutlinedTextField(
                value = s.doctorEmail,
                onValueChange = { repo.reportSettings = s.copy(doctorEmail = it) },
                label = { Text("Clinician email") },
                modifier = Modifier.fillMaxWidth(),
            )
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Include every answer at the end", Modifier.weight(1f))
                Switch(checked = s.includeRawAnswers, onCheckedChange = { repo.reportSettings = s.copy(includeRawAnswers = it) })
            }
        }
    }
}
