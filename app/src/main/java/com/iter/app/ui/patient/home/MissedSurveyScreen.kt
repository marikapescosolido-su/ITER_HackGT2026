package com.iter.app.ui.patient.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.domain.Streak
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.buttons.ButtonSize
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.TertiaryButton

/** Wireflow "Missed Survey?": Primary "Take it now" + Tertiary "Not today". No guilt. */
@Composable
fun MissedSurveyScreen(onTakeNow: () -> Unit, onNotToday: () -> Unit) {
    val streak = Streak.compute(DemoRepository.checkIns)
    ScreenColumn(title = "Missed a day?") {
        Text("That's okay. Some days are harder than others.", style = MaterialTheme.typography.bodyLarge)
        Text(
            if (streak.usedGraceDay || streak.days > 0) "Your streak is safe: one missed day is covered." else "You can start again today.",
            style = MaterialTheme.typography.bodyMedium,
        )
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton("Take it now", onTakeNow, Modifier.fillMaxWidth(), size = ButtonSize.Large)
            TertiaryButton("Not today", onNotToday)
        }
    }
}
