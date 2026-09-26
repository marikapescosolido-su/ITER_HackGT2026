package com.iter.app.ui.patient.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.domain.Streak
import com.iter.app.domain.SupportMessages
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.buttons.IconCircleButton
import com.iter.app.ui.components.buttons.StreakChip
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.navigation.Routes
import com.iter.app.ui.patient.concern.ConcernAlertOverlay
import com.iter.app.domain.ConcernCheck
import com.iter.app.tracking.InteractionLog
import java.time.LocalTime

/** Home dashboard (wireflow). Sections live in HomeSections.kt. */
@Composable
fun PatientHomeScreen(onBack: () -> Unit, onOpen: (String) -> Unit) {
    val repo = DemoRepository
    val streak = Streak.compute(repo.checkIns)

    ScreenColumn(
        title = SupportMessages.greeting(repo.patient.name, LocalTime.now().hour),
        topBar = { HomeTopBar(onBack, onOpen) },
    ) {
        StreakChip("${streak.days}-day streak")

        if (repo.todaysCheckIn == null) DailySurveyPrompt(onOpen) else CheckedInCard(streak.message)
        LatestNudgeCard()
        WeeklyQuestionsCard(onOpen)
        TrendCard()
        TertiaryButton("Preview my report", { onOpen(Routes.REPORT) })
        CrisisBanner()
    }

    val manyOpens = repo.sharing.interactionTracking && ConcernCheck.fromAppOpens(InteractionLog.opensToday())
    if (repo.concernPending || (manyOpens && !repo.concernDismissedToday)) {
        ConcernAlertOverlay(
            onTalk = {
                repo.concernPending = false
                repo.concernDismissedToday = true
                onOpen(Routes.VOICE_CHECK_IN)
            },
            onOkay = {
                repo.concernPending = false
                repo.concernDismissedToday = true
            },
        )
    }
}

/** Back (demo only) on the left; the three dashboard icon buttons on the right. */
@Composable
private fun HomeTopBar(onBack: () -> Unit, onOpen: (String) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Row(Modifier.weight(1f)) { TertiaryButton("Back", onBack) }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            IconCircleButton(IterIcons.Bell, "Notifications", { onOpen(Routes.NOTIFICATIONS) })
            IconCircleButton(IterIcons.Share, "Share settings", { onOpen(Routes.SHARING) })
            IconCircleButton(IterIcons.User, "Account settings", { onOpen(Routes.ACCOUNT) })
        }
    }
}

@Composable
internal fun BodyText(text: String) = Text(text, style = MaterialTheme.typography.bodyMedium)
