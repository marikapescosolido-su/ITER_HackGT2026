package com.iter.app.ui.patient.checkin

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
import androidx.compose.ui.unit.dp
import com.iter.app.data.model.Question
import com.iter.app.ui.components.ScaleQuestion
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ChoiceChip
import com.iter.app.ui.theme.IterTheme
import kotlin.math.roundToInt

@Composable
private fun Sliders(state: CheckInState, questions: List<Question>) {
    questions.forEach { q ->
        ScaleQuestion(
            prompt = q.prompt,
            value = state.scores[q] ?: 5f,
            onValueChange = { state.scores[q] = it },
            lowLabel = q.lowLabel,
            highLabel = q.highLabel,
        )
    }
}

@Composable
fun FeelingStep(state: CheckInState) {
    SectionCard { Sliders(state, state.emotionalQuestions) }
}

@Composable
fun BodyStep(state: CheckInState) {
    SectionCard {
        ScaleQuestion(
            prompt = "Hours of sleep last night",
            value = state.sleepHours,
            onValueChange = { state.sleepHours = (it * 2).roundToInt() / 2f },
            lowLabel = "0",
            highLabel = "12+",
            max = 12f,
            steps = 23,
            valueText = "${state.sleepHours} h",
        )
        Sliders(state, state.physicalQuestions)
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
        Text("A few words about your day, if you'd like. Optional.", style = MaterialTheme.typography.bodyMedium, color = IterTheme.chrome.charcoal)
        OutlinedTextField(
            value = state.note,
            onValueChange = { state.note = it },
            placeholder = { Text("What affected your day?") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
