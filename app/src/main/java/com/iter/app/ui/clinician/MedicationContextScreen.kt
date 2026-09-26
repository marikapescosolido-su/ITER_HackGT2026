package com.iter.app.ui.clinician

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.ClinicianNote
import com.iter.app.data.model.DataSource
import com.iter.app.domain.DoseComparison
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.theme.IterTheme

/**
 * Provider "Medication context": full medication list, before/after a dose change, and
 * Primary "Suggest medication note". The note is decision support for the care team,
 * never an instruction to the patient.
 */
@Composable
fun MedicationContextScreen(onBack: () -> Unit) {
    val repo = DemoRepository
    val p = repo.patient
    var note by remember { mutableStateOf("") }

    ScreenColumn(title = "Medication context", subtitle = "${p.name} · ${p.condition}", onBack = onBack) {
        SectionCard(title = "Current medications", source = DataSource.Patient) {
            p.medications.forEach { m ->
                Text("${m.name} ${m.dose} · ${m.purpose}${if (m.isMonitored) " (monitored)" else ""}", style = MaterialTheme.typography.bodyMedium)
            }
            Text("Previously: ${p.baseline.previousMedications.joinToString()}", style = MaterialTheme.typography.bodySmall, color = IterTheme.chrome.charcoal)
        }

        p.doseChanges.forEach { change ->
            val cmp = DoseComparison.build(change, repo.checkIns)
            SectionCard(title = "${change.medication} ${change.from} → ${change.to} on ${formatDate(change.date)}", source = DataSource.Patient) {
                Text("Average of ${cmp.days} days before vs after", style = MaterialTheme.typography.labelMedium, color = IterTheme.chrome.charcoal)
                DoseComparison.QUESTIONS.forEach { q ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(q.label, style = MaterialTheme.typography.bodyMedium)
                        Text("${formatAverage(cmp.before[q])} → ${formatAverage(cmp.after[q])}", style = MaterialTheme.typography.titleSmall)
                    }
                }
                val effects = (cmp.sideEffectsBefore.keys + cmp.sideEffectsAfter.keys).distinct()
                effects.forEach { e ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(e.label, style = MaterialTheme.typography.bodySmall)
                        Text("${cmp.sideEffectsBefore[e] ?: 0} → ${cmp.sideEffectsAfter[e] ?: 0} days", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Text("Change over time, not proof the dose caused it.", style = MaterialTheme.typography.labelSmall, color = IterTheme.chrome.charcoal)
            }
        }

        SectionCard(title = "Notes for the care team", source = DataSource.Clinician) {
            repo.clinicianNotes.forEach { Text("${formatDate(it.date)}: ${it.text}", style = MaterialTheme.typography.bodyMedium) }
            OutlinedTextField(
                note, { note = it },
                label = { Text("e.g. Review tiredness at next visit") },
                minLines = 2,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        PrimaryButton(
            "Suggest medication note",
            {
                repo.clinicianNotes.add(ClinicianNote(repo.today, note.trim()))
                note = ""
            },
            Modifier.fillMaxWidth(),
            enabled = note.isNotBlank(),
        )
    }
}
