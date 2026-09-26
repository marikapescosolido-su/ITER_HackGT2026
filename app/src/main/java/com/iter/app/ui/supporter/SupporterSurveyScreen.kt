package com.iter.app.ui.supporter

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.data.model.SupporterObservation
import com.iter.app.ui.components.ScaleQuestion
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import kotlin.math.roundToInt

/** Weekly survey from the supporter's point of view. Shown separately from the patient's answers. */
@Composable
fun SupporterSurveyScreen(onBack: () -> Unit) {
    val name = DemoRepository.patient.name
    var mood by remember { mutableFloatStateOf(5f) }
    var energy by remember { mutableFloatStateOf(5f) }
    var sleep by remember { mutableFloatStateOf(5f) }
    var social by remember { mutableFloatStateOf(5f) }
    var note by remember { mutableStateOf("") }
    var saved by remember { mutableStateOf(false) }

    ScreenColumn(
        title = "This week, from your view",
        subtitle = "Your view is shown next to $name's own answers, never instead of them. $name can see that this survey exists.",
        onBack = onBack,
    ) {
        if (saved) {
            SectionCard { Text("Thank you. Your observation was saved.") }
            return@ScreenColumn
        }
        SectionCard {
            ScaleQuestion("How did $name's mood seem?", mood, { mood = it }, "Very low", "Very good")
            ScaleQuestion("Their energy?", energy, { energy = it }, "None", "Plenty")
            ScaleQuestion("Their sleep, as far as you know?", sleep, { sleep = it }, "Poor", "Good")
            ScaleQuestion("Time with other people?", social, { social = it }, "Withdrawn", "Usual")
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Anything you noticed? (optional)") },
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Button(
            onClick = {
                DemoRepository.saveObservation(
                    SupporterObservation(
                        date = DemoRepository.today,
                        supporterName = DEMO_SUPPORTER,
                        mood = mood.roundToInt(),
                        energy = energy.roundToInt(),
                        sleep = sleep.roundToInt(),
                        social = social.roundToInt(),
                        note = note.trim(),
                    ),
                )
                saved = true
            },
            modifier = Modifier.fillMaxWidth(),
        ) { Text("Save") }
        Text("Describe what you saw, not a diagnosis.", style = MaterialTheme.typography.bodySmall)
    }
}
