package com.iter.app.ui.patient.home

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.DataSource
import com.iter.app.data.model.Question
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.ChartSeries
import com.iter.app.ui.components.LineChart
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ButtonSize
import com.iter.app.ui.components.buttons.HelplineButton
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.SecondaryButton
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.navigation.Routes
import com.iter.app.ui.theme.IterTheme
import java.time.temporal.ChronoUnit

/** Wireflow "Daily Survey Prompt": Primary L "Start today's check-in" + Secondary "Remind me later". */
@Composable
internal fun DailySurveyPrompt(onOpen: (String) -> Unit) {
    val repo = DemoRepository
    SectionCard(title = "Today's check-in") {
        BodyText(SupportMessages.CHECK_IN_INTRO)
        PrimaryButton("Start today's check-in", { onOpen(Routes.CHECK_IN) }, Modifier.fillMaxWidth(), size = ButtonSize.Large)
        if (repo.remindLaterRequested) {
            Text("We'll remind you at ${repo.reminderTime}.", style = MaterialTheme.typography.bodySmall, color = IterTheme.chrome.charcoal)
        } else {
            SecondaryButton("Remind me later", { repo.remindLaterRequested = true }, Modifier.fillMaxWidth())
        }
    }
}

@Composable
internal fun CheckedInCard(message: String) {
    SectionCard(title = "Today's check-in") {
        BodyText("You've completed today's check-in. $message")
    }
}

@Composable
internal fun LatestNudgeCard() {
    val nudge = DemoRepository.nudges.firstOrNull() ?: return
    SectionCard(title = "From ${nudge.from}", source = DataSource.Supporter) {
        Text("“${nudge.message}”", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
internal fun WeeklyQuestionsCard(onOpen: (String) -> Unit) {
    val repo = DemoRepository
    val last = repo.phq9s.lastOrNull()
    val due = last == null || ChronoUnit.DAYS.between(last.date, repo.today) >= 7
    if (!due) return
    SectionCard(title = "Weekly questions") {
        BodyText("Nine slightly longer questions, once a week. About 2 minutes.")
        TertiaryButton("Start weekly questions", { onOpen(Routes.PHQ9) })
    }
}

@Composable
internal fun TrendCard() {
    val repo = DemoRepository
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
            "Your answers help ${repo.patient.clinicianName} see how treatment is going.",
            style = MaterialTheme.typography.labelMedium,
            color = IterTheme.chrome.charcoal,
        )
    }
}

/** Standing crisis banner: the reserved terracotta helpline button. Always available, never a pop-up. */
@Composable
internal fun CrisisBanner() {
    val context = LocalContext.current
    SectionCard {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BodyText("Need to talk to someone right now? It's free and confidential, any time.")
        }
        HelplineButton(
            "Call 988 Suicide & Crisis Lifeline",
            { context.startActivity(Intent(Intent.ACTION_DIAL, "tel:988".toUri())) },
            Modifier.fillMaxWidth(),
            icon = IterIcons.Phone,
        )
    }
}
