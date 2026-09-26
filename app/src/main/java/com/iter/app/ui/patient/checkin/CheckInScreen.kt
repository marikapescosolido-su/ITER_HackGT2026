package com.iter.app.ui.patient.checkin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.CheckIn
import com.iter.app.data.model.SideEffect
import com.iter.app.domain.DailyQuestionPlan
import com.iter.app.domain.MedicationProfiles
import com.iter.app.tracking.InteractionLog
import com.iter.app.ui.components.ScaleQuestion
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import kotlin.math.roundToInt

/** The daily 1-3 minute check-in. Questions come from DailyQuestionPlan + the medication profile. */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckInScreen(onBack: () -> Unit, onSubmitted: () -> Unit) {
    val repo = DemoRepository
    val medication = repo.patient.monitoredMedication
    val profile = remember(medication) { MedicationProfiles.forMedication(medication.name) }
    val questions = remember { DailyQuestionPlan.questionsFor(repo.today, profile) }
    val startedAt = remember {
        InteractionLog.record(InteractionLog.Event.CheckInStarted)
        System.currentTimeMillis()
    }

    val scores = remember { mutableStateMapOf<com.iter.app.data.model.Question, Float>().apply { questions.forEach { put(it, 5f) } } }
    var sleepHours by remember { mutableFloatStateOf(7f) }
    var tookMedication by remember { mutableStateOf<Boolean?>(null) }
    val sideEffects = remember { mutableStateMapOf<SideEffect, Boolean>() }
    var note by remember { mutableStateOf("") }

    ScreenColumn(title = "Today's check-in", subtitle = "About a minute. There are no right answers.", onBack = onBack) {
        SectionCard(title = "How you're feeling") {
            questions.forEach { q ->
                ScaleQuestion(
                    prompt = q.prompt,
                    value = scores[q] ?: 5f,
                    onValueChange = { scores[q] = it },
                    lowLabel = q.lowLabel,
                    highLabel = q.highLabel,
                )
            }
        }

        SectionCard(title = "Sleep") {
            ScaleQuestion(
                prompt = "Hours of sleep last night",
                value = sleepHours,
                onValueChange = { sleepHours = (it * 2).roundToInt() / 2f },
                lowLabel = "0",
                highLabel = "12+",
                max = 12f,
                steps = 23,
                valueText = "$sleepHours h",
            )
        }

        SectionCard(title = "${medication.name} ${medication.dose}") {
            Text("Did you take it today?")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = tookMedication == true, onClick = { tookMedication = true }, label = { Text("Yes") })
                FilterChip(selected = tookMedication == false, onClick = { tookMedication = false }, label = { Text("Not today") })
            }
            Text("Anything you noticed? (optional)")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                profile.sideEffects.forEach { effect ->
                    FilterChip(
                        selected = sideEffects[effect] == true,
                        onClick = { sideEffects[effect] = sideEffects[effect] != true },
                        label = { Text(effect.label) },
                    )
                }
            }
        }

        SectionCard(title = "Anything else? (optional)") {
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                placeholder = { Text("What affected your day?") },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Button(
            onClick = {
                repo.saveCheckIn(
                    CheckIn(
                        date = repo.today,
                        scores = scores.mapValues { it.value.roundToInt() },
                        sleepHours = sleepHours,
                        tookMedication = tookMedication ?: false,
                        sideEffects = sideEffects.filterValues { it }.keys,
                        note = note.trim(),
                        durationSeconds = ((System.currentTimeMillis() - startedAt) / 1000).toInt(),
                    ),
                )
                InteractionLog.record(InteractionLog.Event.CheckInCompleted)
                onSubmitted()
            },
            enabled = tookMedication != null,
            modifier = Modifier.fillMaxWidth(),
        ) { Text("Save check-in") }
        if (tookMedication == null) {
            Text("Answer the medication question to save.", style = MaterialTheme.typography.labelMedium)
        }
    }
}
