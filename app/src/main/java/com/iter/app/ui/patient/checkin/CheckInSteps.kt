package com.iter.app.ui.patient.checkin

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.iter.app.data.model.AppetiteDirection
import com.iter.app.data.model.Question
import com.iter.app.domain.Safety
import com.iter.app.ui.components.ScaleQuestion
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ChoiceChip
import com.iter.app.ui.components.buttons.HelplineButton
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.theme.IterTheme
import kotlin.math.roundToInt

/** One slider question per screen, with its "i" explanation and any follow-up it needs. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuestionStep(state: CheckInState, question: Question) {
    val value = state.scores[question]
    SectionCard {
        ScaleQuestion(
            prompt = question.prompt,
            value = value,
            onValueChange = { state.answer(question, it) },
            lowLabel = "0 — ${question.lowLabel}",
            highLabel = "10 — ${question.highLabel}",
            steps = 0,
            valueText = value?.let { ((it * 10).roundToInt() / 10f).toString() }.orEmpty(),
            whyWeAsk = question.why,
        )
        if (state.skipped[question] == true) {
            Text("Skipped", style = MaterialTheme.typography.labelMedium, color = IterTheme.chrome.charcoal)
        }
    }

    if (question == Question.Appetite && (value ?: 0f) > 0f) {
        SectionCard {
            Text("Did you mostly feel like eating less, eating more, or did it change during the day?", style = MaterialTheme.typography.bodyLarge)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                AppetiteDirection.entries.forEach { d ->
                    ChoiceChip(d.label, state.appetiteDirection == d, { state.appetiteDirection = d })
                }
            }
        }
    }

    if (question == Question.Sleep) {
        SectionCard {
            ScaleQuestion(
                prompt = "Roughly how many hours did you sleep last night?",
                value = state.sleepHours,
                onValueChange = { state.sleepHours = (it * 2).roundToInt() / 2f },
                lowLabel = "0",
                highLabel = "12+",
                max = 12f,
                steps = 23,
                valueText = "${state.sleepHours} h",
            )
        }
    }

    if (question == Question.SelfHarm && (value ?: 0f) > 0f) SafetySupportCard(immediate = value!! >= Safety.IMMEDIATE_DANGER)
}

/** Shown straight away on the safety question, rather than waiting for a later report. */
@Composable
private fun SafetySupportCard(immediate: Boolean) {
    val context = LocalContext.current
    SectionCard(title = if (immediate) "Please reach out now" else "You don't have to go through this alone") {
        Text(
            if (immediate) {
                "If you might act on these thoughts, call 911 or the 988 Lifeline now. You can finish this check-in later."
            } else {
                "Thank you for telling us. Talking to someone can help, any time."
            },
            style = MaterialTheme.typography.bodyMedium,
        )
        Safety.resources.filter { it.phone != null }.forEach { r ->
            Text(r.detail, style = MaterialTheme.typography.bodySmall)
            HelplineButton("Call ${r.phone}", {
                context.startActivity(Intent(Intent.ACTION_DIAL, "tel:${r.phone}".toUri()))
            }, Modifier.fillMaxWidth(), icon = IterIcons.Phone)
        }
        Text(Safety.DISCLAIMER, style = MaterialTheme.typography.bodySmall)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MedicationStep(state: CheckInState) {
    SectionCard(title = "${state.medication.name} ${state.medication.dose}") {
        Text("Did you take it today?", style = MaterialTheme.typography.bodyLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ChoiceChip("Yes", state.tookMedication == true, { state.tookMedication = true })
            ChoiceChip("Not today", state.tookMedication == false, { state.tookMedication = false })
        }
    }
    SectionCard(title = "Anything you noticed? (optional)") {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            state.profile.sideEffects.forEach { effect ->
                ChoiceChip(effect.label, state.sideEffects[effect] == true, {
                    state.sideEffects[effect] = state.sideEffects[effect] != true
                })
            }
        }
    }
}

@Composable
fun NoteStep(state: CheckInState) {
    SectionCard {
        Text(
            "Is there anything else about today that you want to remember or want your care team to understand? " +
                "For example, events that could explain changes, separate from your medication. Optional.",
            style = MaterialTheme.typography.bodyMedium,
            color = IterTheme.chrome.charcoal,
        )
        OutlinedTextField(
            value = state.note,
            onValueChange = { state.note = it },
            placeholder = { Text("What affected your day?") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
