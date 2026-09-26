package com.iter.app.ui.patient.checkin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.model.CheckIn
import com.iter.app.tracking.InteractionLog
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.theme.IterTheme

/**
 * Daily check-in, one question per screen (Daily_Check_In_Questions.md), then medication and an optional note.
 * Question screens have Back + Skip + Next; Next only enables once the slider has been moved.
 */
@Composable
fun CheckInScreen(onBack: () -> Unit, onSubmitted: (CheckIn) -> Unit) {
    val state = remember {
        InteractionLog.record(InteractionLog.Event.CheckInStarted)
        CheckInState()
    }
    var step by remember { mutableIntStateOf(0) }
    val questionCount = state.questions.size
    val medicationStep = questionCount
    val last = questionCount + 1
    val question = state.questions.getOrNull(step)

    val title = when (step) {
        medicationStep -> "Your medication"
        last -> "Anything else?"
        else -> "How has today been?"
    }
    val subtitle = if (question != null) "Question ${step + 1} of $questionCount" else "Almost done"
    val canContinue = when {
        question != null -> state.scores[question] != null
        step == medicationStep -> state.tookMedication != null
        else -> true
    }
    val next: () -> Unit = {
        if (step < last) {
            step++
        } else {
            val saved = state.save()
            InteractionLog.record(InteractionLog.Event.CheckInCompleted)
            onSubmitted(saved)
        }
    }

    ScreenColumn(title = title, subtitle = subtitle) {
        LinearProgressIndicator(
            progress = { (step + 1) / (last + 1).toFloat() },
            modifier = Modifier.fillMaxWidth(),
            trackColor = IterTheme.chrome.hairline,
        )
        if (step == 0) {
            Text(
                "Move the slider to the place that feels closest. There are no right or wrong answers, " +
                    "and you can skip anything you do not want to answer. Your answers are not monitored in real time; " +
                    "your care team sees them in your next report.",
                style = MaterialTheme.typography.bodyMedium,
                color = IterTheme.chrome.charcoal,
            )
        }
        when {
            question != null -> QuestionStep(state, question)
            step == medicationStep -> MedicationStep(state)
            else -> NoteStep(state)
        }
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(Modifier.weight(1f)) { TertiaryButton("Back", { if (step == 0) onBack() else step-- }) }
            if (question != null) {
                TertiaryButton("Skip", {
                    state.skip(question)
                    next()
                })
            }
            PrimaryButton(if (step == last) "Save" else "Next", next, enabled = canContinue)
        }
    }
}
