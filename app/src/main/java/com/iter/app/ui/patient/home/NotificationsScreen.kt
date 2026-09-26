package com.iter.app.ui.patient.home

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.navigation.Routes
import com.iter.app.ui.theme.IterTheme

/** In-app notification list (demo). Tapping the missed check-in opens the Missed Survey screen. */
@Composable
fun NotificationsScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    val repo = DemoRepository
    ScreenColumn(title = "Notifications", onBack = onBack) {
        repo.nudges.forEach { nudge ->
            Item("${nudge.from} sent you a nudge", "“${nudge.message}”")
        }
        if (repo.todaysCheckIn == null) {
            Item("Today's check-in", SupportMessages.reminder(hadHardDayYesterday = false))
        }
        Item("A day without a check-in", "That's okay. Tap if you'd like to catch up.") { onOpen(Routes.MISSED_SURVEY) }
        Item("Report shared", "Your weekly report was sent to ${repo.patient.clinicianName}.")
    }
}

@Composable
private fun Item(title: String, body: String, onClick: (() -> Unit)? = null) {
    SectionCard(modifier = if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier) {
        Text(title, style = MaterialTheme.typography.titleSmall)
        Text(body, style = MaterialTheme.typography.bodyMedium, color = IterTheme.chrome.charcoal)
    }
}
