package com.iter.app.ui.supporter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.Question
import com.iter.app.data.model.SupportRole
import com.iter.app.domain.Streak
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.ChartSeries
import com.iter.app.ui.components.LineChart
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
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
        subtitle = "You're a ${me.role.label.lowercase()}. ${me.role.description}",
        onBack = onBack,
    ) {
        SectionCard(title = "Today") {
            Text(if (done) "${patient.name} has checked in today." else "${patient.name} hasn't checked in yet today.")
            Text("Streak: ${streak.days} days", style = MaterialTheme.typography.bodyMedium)
        }

        if (repo.sharing.supporterMoodNotifications) {
            SupportMessages.supporterUpdate(patient.name, yesterdayMood, repo.sharing.notificationDetail)?.let {
                SectionCard(title = "Update", containerColor = MaterialTheme.colorScheme.secondaryContainer) { Text(it) }
            }
        }

        if (me.challengeEnabled) {
            val goal = 30
            SectionCard(title = "Shared goal with ${patient.name}") {
                Text("${streak.days.coerceAtMost(goal)} of $goal days of check-ins together.")
                Text("Celebrate showing up, not scores.", style = MaterialTheme.typography.bodySmall)
            }
        }

        val recent = repo.checkIns.takeLast(14)
        SectionCard(title = "Mood, last two weeks") {
            LineChart(
                series = listOf(ChartSeries("Mood", recent.map { it.score(Question.Mood)?.toFloat() }, MaterialTheme.colorScheme.primary)),
                yMax = 10f,
            )
        }

        if (me.role != SupportRole.Viewer && !done) {
            Button(onClick = { onOpen(Routes.NUDGE) }, modifier = Modifier.fillMaxWidth()) { Text("Send a kind nudge") }
        }
        OutlinedButton(onClick = { onOpen(Routes.SUPPORTER_SURVEY) }, modifier = Modifier.fillMaxWidth()) {
            Text("Weekly observation (2 min)")
        }
        if (me.role == SupportRole.Editor) {
            // TODO(team): "Suggest a correction" flow. Edits must be attributed and approved by the patient.
            Text("As an editor you can suggest corrections. ${patient.name} approves every change.", style = MaterialTheme.typography.bodySmall)
        }
    }
}
