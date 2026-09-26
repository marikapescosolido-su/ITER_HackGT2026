package com.iter.app.ui.patient.sharing

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.theme.IterTheme

@Composable
fun PrivacyToggles() {
    val repo = DemoRepository
    val s = repo.sharing
    SectionCard(title = "Your care team") {
        ToggleRow("Share reports with ${repo.patient.clinicianName}", s.shareWithClinician) {
            repo.sharing = s.copy(shareWithClinician = it)
        }
        ToggleRow("Include supporter observations in reports", s.includeSupporterObservations) {
            repo.sharing = s.copy(includeSupporterObservations = it)
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
            color = IterTheme.chrome.charcoal,
        )
    }
}

@Composable
fun ToggleRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onChange)
    }
}
