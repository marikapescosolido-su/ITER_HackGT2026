package com.iter.app.ui.patient.sharing

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.NotificationDetail
import com.iter.app.data.model.SupportRole
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.CautionButton
import com.iter.app.ui.components.buttons.ChoiceChip
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.SegmentedControl
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.theme.IterTheme

/**
 * Share mode settings (wireflow): assign role with the segmented control, "End shared access" as the
 * caution button, and privacy toggles. The patient controls everything here.
 */
@Composable
fun SharingScreen(onBack: () -> Unit, onInvite: () -> Unit) {
    val repo = DemoRepository
    val s = repo.sharing

    ScreenColumn(title = "Share settings", subtitle = "You decide who sees what. You can change this any time.", onBack = onBack) {
        repo.supporters.forEach { supporter ->
            SectionCard(title = "${supporter.name} · ${supporter.relation}") {
                Text("Assign role", style = MaterialTheme.typography.labelMedium, color = IterTheme.chrome.charcoal)
                SegmentedControl(
                    options = SupportRole.entries,
                    selected = supporter.role,
                    onSelect = { repo.setRole(supporter.name, it) },
                    label = { it.label },
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(supporter.role.description, style = MaterialTheme.typography.bodySmall, color = IterTheme.chrome.charcoal)
                CautionButton("End shared access", { repo.removeSupporter(supporter.name) })
            }
        }
        if (repo.supporters.isEmpty()) {
            Text("No one has access right now.", style = MaterialTheme.typography.bodyMedium)
        }
        PrimaryButton("Invite someone", onInvite, Modifier.fillMaxWidth(), icon = IterIcons.Plus)

        SectionCard(title = "Updates to supporters") {
            ToggleRow("Let supporters know when I had a harder day", s.supporterMoodNotifications) {
                repo.sharing = s.copy(supporterMoodNotifications = it)
            }
            if (s.supporterMoodNotifications) {
                NotificationDetail.entries.forEach { detail ->
                    ChoiceChip(detail.label, s.notificationDetail == detail, { repo.sharing = s.copy(notificationDetail = detail) })
                }
            }
        }
        PrivacyToggles()
    }
}
