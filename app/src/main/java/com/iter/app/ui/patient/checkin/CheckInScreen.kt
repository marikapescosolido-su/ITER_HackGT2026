package com.iter.app.ui.patient.checkin

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.iter.app.data.model.CheckIn
import com.iter.app.tracking.InteractionLog
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.theme.IterTheme

/**
 * Daily check-in, one topic per screen. Every screen has the same pair:
 * Tertiary "Back" + Primary "Next" (wireflow: Physical/Emotional Qs).
 */
@Composable
fun CheckInScreen(onBack: () -> Unit, onSubmitted: (CheckIn) -> Unit) {
    val state = remember {
        InteractionLog.record(InteractionLog.Event.CheckInStarted)
        CheckInState()
    }
    var step by remember { mutableIntStateOf(0) }
    val titles = listOf("How you're feeling", "Your body", "Your medication", "Anything else?")
    val last = titles.lastIndex
    val canContinue = step != 2 || state.tookMedication != null

    ScreenColumn(title = titles[step], subtitle = "Step ${step + 1} of ${titles.size} · about a minute in total") {
        LinearProgressIndicator(
            progress = { (step + 1) / titles.size.toFloat() },
            modifier = Modifier.fillMaxWidth(),
            trackColor = IterTheme.chrome.hairline,
        )
        when (step) {
            0 -> FeelingStep(state)
            1 -> BodyStep(state)
            2 -> MedicationStep(state)
            else -> NoteStep(state)
        }
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.weight(1f)) { TertiaryButton("Back", { if (step == 0) onBack() else step-- }) }
            PrimaryButton(
                if (step == last) "Save" else "Next",
                {
                    if (step < last) {
                        step++
                    } else {
                        val saved = state.save()
                        InteractionLog.record(InteractionLog.Event.CheckInCompleted)
                        onSubmitted(saved)
                    }
                },
                enabled = canContinue,
            )
        }
    }
}
