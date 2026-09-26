package com.iter.app.ui.patient.sharing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.NotificationDetail
import com.iter.app.data.model.SupportRole
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard

/** Patient controls who sees what (README: "Privacy, consent, and patient control"). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharingScreen(onBack: () -> Unit) {
    val repo = DemoRepository
    val s = repo.sharing

    ScreenColumn(title = "Sharing & privacy", subtitle = "You decide who sees what. You can change this any time.", onBack = onBack) {
        SectionCard(title = "Your care team") {
            ToggleRow("Share reports with ${repo.patient.clinicianName}", s.shareWithClinician) {
                repo.sharing = s.copy(shareWithClinician = it)
            }
            ToggleRow("Include supporter observations in reports", s.includeSupporterObservations) {
                repo.sharing = s.copy(includeSupporterObservations = it)
            }
        }

        SectionCard(title = "People supporting you") {
            repo.supporters.forEach { supporter ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("${supporter.name} (${supporter.relation})", style = MaterialTheme.typography.titleSmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        SupportRole.entries.forEach { role ->
                            FilterChip(
                                selected = supporter.role == role,
                                onClick = { repo.setRole(supporter.name, role) },
                                label = { Text(role.label) },
                            )
                        }
                    }
                    Text(supporter.role.description, style = MaterialTheme.typography.bodySmall)
                    TextButton(onClick = { repo.removeSupporter(supporter.name) }) { Text("Remove access") }
                }
            }
            if (repo.supporters.isEmpty()) Text("No one has access right now.")
        }

        SectionCard(title = "Updates to supporters") {
            ToggleRow("Let supporters know when I had a harder day", s.supporterMoodNotifications) {
                repo.sharing = s.copy(supporterMoodNotifications = it)
            }
            if (s.supporterMoodNotifications) {
                NotificationDetail.entries.forEach { detail ->
                    FilterChip(
                        selected = s.notificationDetail == detail,
                        onClick = { repo.sharing = s.copy(notificationDetail = detail) },
                        label = { Text(detail.label) },
                    )
                }
            }
        }

        SectionCard(title = "Optional") {
            ToggleRow("Use app activity patterns as extra context", s.interactionTracking) {
                repo.sharing = s.copy(interactionTracking = it)
            }
            ToggleRow("Allow my de-identified data to be used for research", s.researchConsent) {
                repo.sharing = s.copy(researchConsent = it)
            }
            Text(
                "Research is separate from your care. Saying no never affects your treatment.",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onChange)
    }
}
