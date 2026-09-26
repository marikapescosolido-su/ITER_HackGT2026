package com.iter.app.ui.supporter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.Question
import com.iter.app.data.model.SupportRole
import com.iter.app.domain.Lantern
import com.iter.app.domain.Streak
import com.iter.app.domain.SupportMessages
import com.iter.app.domain.SupporterSummary
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.UtilityButton
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.components.illustration.LanternArt
import com.iter.app.ui.navigation.Routes

/** What Sam sees. Content depends on the role the patient gave them. */
@Composable
fun SupporterHomeScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    val repo = DemoRepository
    val patient = repo.patient
    val me = repo.supporters.firstOrNull { it.name == DEMO_SUPPORTER }

    if (me == null) {
        ScreenColumn(title = "No access", onBack = onBack) {
            Text("${patient.name} has turned off sharing with you. That's their choice, and it can change later.")
        }
        return
    }

    val streak = Streak.compute(repo.checkIns)
    val done = repo.todaysCheckIn != null
    val yesterdayMood = repo.checkIns.lastOrNull { it.date == repo.today.minusDays(1) }?.score(Question.Mood)

    ScreenColumn(
        title = "Supporting ${patient.name}",
        subtitle = "Your role: ${me.role.label}. ${me.role.description}",
        onBack = onBack,
    ) {
        SectionCard(title = "Today") {
            Text(if (done) "${patient.name} has checked in today." else "${patient.name} hasn't checked in yet today.")
            Text("Streak: ${streak.days} days", style = MaterialTheme.typography.bodyMedium)
            if (me.role != SupportRole.Viewer && !done) {
                Text("A kind word can help. Nudges are limited to one a day.", style = MaterialTheme.typography.bodySmall)
                UtilityButton("Send a nudge", IterIcons.Heart, { onOpen(Routes.NUDGE) }, Modifier.fillMaxWidth())
            }
        }

        if (repo.sharing.supporterMoodNotifications) {
            SupportMessages.supporterUpdate(patient.name, yesterdayMood, repo.sharing.notificationDetail)?.let {
                SectionCard(title = "Update", containerColor = MaterialTheme.colorScheme.secondaryContainer) { Text(it) }
            }
        }

        if (me.challengeEnabled) {
            val goal = 30
            SectionCard(title = "Shared goal with ${patient.name}") {
                val lantern = Lantern.compute(repo.checkIns)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    LanternArt(lantern.brightness, Modifier.size(64.dp))
                    Column {
                        Text("${patient.name}'s lantern: ${lantern.level} of ${Lantern.MAX} this week")
                        Text("${streak.days.coerceAtMost(goal)} of $goal days of check-ins together.", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Text("Celebrate showing up, not scores.", style = MaterialTheme.typography.bodySmall)
            }
        }

        if (repo.sharing.supporterMoodNotifications) {
            SectionCard(title = "This week") {
                Text(SupporterSummary.sentence(patient.name, SupporterSummary.level(repo.checkIns)))
                Text(
                    "You'll never see ${patient.name}'s answers. Only their care team does.",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        SectionCard(title = "Your weekly check-in") {
            Text("How has ${patient.name} seemed to you this week? About 2 minutes.", style = MaterialTheme.typography.bodyMedium)
            PrimaryButton("Start weekly check-in", { onOpen(Routes.SUPPORTER_SURVEY) }, Modifier.fillMaxWidth())
        }
        if (me.role == SupportRole.Editor) {
            // TODO(team): "Suggest a correction" flow. Edits must be attributed and approved by the patient.
            Text("As an editor you can suggest corrections. ${patient.name} approves every change.", style = MaterialTheme.typography.bodySmall)
        }
    }
}
