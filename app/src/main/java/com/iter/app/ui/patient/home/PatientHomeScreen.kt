package com.iter.app.ui.patient.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.DataSource
import com.iter.app.data.model.Question
import com.iter.app.domain.Streak
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.ChartSeries
import com.iter.app.ui.components.LineChart
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.navigation.Routes
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Composable
fun PatientHomeScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    val repo = DemoRepository
    val patient = repo.patient
    val streak = Streak.compute(repo.checkIns)
    val done = repo.todaysCheckIn != null
    val lastPhq = repo.phq9s.lastOrNull()
    val phqDue = lastPhq == null || ChronoUnit.DAYS.between(lastPhq.date, repo.today) >= 7

    ScreenColumn(
        title = SupportMessages.greeting(patient.name, LocalTime.now().hour),
        subtitle = if (done) "You've completed today's check-in." else SupportMessages.CHECK_IN_INTRO,
        onBack = onBack,
    ) {
        if (!done) {
            Button(onClick = { onOpen(Routes.CHECK_IN) }, modifier = Modifier.fillMaxWidth()) {
                Text("Start today's check-in")
            }
        }

        SectionCard(title = "Your streak") {
            Text("${streak.days} days", style = MaterialTheme.typography.headlineSmall)
            Text(streak.message, style = MaterialTheme.typography.bodyMedium)
            if (streak.usedGraceDay) {
                Text("A missed day was covered by your grace day.", style = MaterialTheme.typography.labelMedium)
            }
        }

        repo.nudges.firstOrNull()?.let { nudge ->
            SectionCard(title = "From ${nudge.from}", source = DataSource.Supporter) {
                Text("“${nudge.message}”", style = MaterialTheme.typography.bodyLarge)
            }
        }

        if (phqDue) {
            SectionCard(title = "Weekly questionnaire") {
                Text("A slightly longer set of 9 questions, once a week. About 2 minutes.")
                OutlinedButton(onClick = { onOpen(Routes.PHQ9) }) { Text("Start weekly questions") }
            }
        }

        val recent = repo.checkIns.takeLast(14)
        SectionCard(title = "Your last two weeks") {
            LineChart(
                series = listOf(
                    ChartSeries("Mood", recent.map { it.score(Question.Mood)?.toFloat() }, MaterialTheme.colorScheme.primary),
                    ChartSeries("Energy", recent.map { it.score(Question.Energy)?.toFloat() }, MaterialTheme.colorScheme.secondary),
                ),
                yMax = 10f,
            )
            Text(
                "Your answers help ${patient.clinicianName} see how treatment is going.",
                style = MaterialTheme.typography.labelMedium,
            )
        }

        OutlinedButton(onClick = { onOpen(Routes.SHARING) }, modifier = Modifier.fillMaxWidth()) {
            Text("Sharing & privacy")
        }
        OutlinedButton(onClick = { onOpen(Routes.REPORT) }, modifier = Modifier.fillMaxWidth()) {
            Text("Preview my report")
        }
    }
}
