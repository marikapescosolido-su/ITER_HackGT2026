package com.iter.app.ui.patient.home

import android.content.Intent
import com.iter.app.ui.components.illustration.BearPath
import com.iter.app.ui.components.buttons.StreakChip
import com.iter.app.domain.Streak
import com.iter.app.domain.Lantern
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
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
import com.iter.app.domain.ReportSchedule
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ButtonSize
import com.iter.app.ui.components.buttons.HelplineButton
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.SecondaryButton
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.navigation.Routes
import com.iter.app.ui.theme.IterTheme

/** Bear + lantern header. Shows progress (days checked in), never how the patient felt. */
@Composable
internal fun JourneyCard() {
    val repo = DemoRepository
    val lantern = Lantern.compute(repo.checkIns)
    val streak = Streak.compute(repo.checkIns)
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        BearPath(lantern.level)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            StreakChip("${streak.days}-day streak")
            Text(lantern.message, style = MaterialTheme.typography.bodySmall, color = IterTheme.chrome.charcoal)
        }
    }
}

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

/** The patient sees *when* the report goes out, never what is in it. */
@Composable
internal fun ReportScheduleCard() {
    val repo = DemoRepository
    SectionCard(title = "Your care team") {
        BodyText("${repo.patient.clinicianName} receives your report ${ReportSchedule.label(repo.reportSettings.intervalDays)}, automatically.")
        Text(
            "Only your care team sees your answers. That way each day is just a quick check-in, not a score.",
            style = MaterialTheme.typography.bodySmall,
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
