package com.iter.app.ui.patient.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.DemoRepository
import com.iter.app.ui.components.ScaleQuestion
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import kotlin.math.roundToInt

/**
 * Baseline assessment, shortened for the demo (README lists the full set).
 * Steps: welcome -> medication -> baseline -> family history. Each step explains why it asks.
 * TODO(team): more steps (other medications, previous treatments, consent) if time allows.
 */
@Composable
fun OnboardingScreen(onBack: () -> Unit, onFinished: () -> Unit) {
    val repo = DemoRepository
    val patient = repo.patient
    val totalSteps = 4
    var step by remember { mutableIntStateOf(0) }
    var name by remember { mutableStateOf(patient.name) }
    var medication by remember { mutableStateOf(patient.monitoredMedication.name) }
    var dose by remember { mutableStateOf(patient.monitoredMedication.dose) }
    var typicalSleep by remember { mutableFloatStateOf(patient.baseline.typicalSleepHours) }
    var typicalMood by remember { mutableFloatStateOf(patient.baseline.typicalMood.toFloat()) }
    var familyHistory by remember { mutableStateOf(patient.baseline.familyHistory.joinToString(", ")) }

    fun finish() {
        val meds = patient.medications.map {
            if (it.isMonitored) it.copy(name = medication.trim(), dose = dose.trim()) else it
        }
        repo.patient = patient.copy(
            name = name.trim().ifEmpty { patient.name },
            medications = meds,
            baseline = patient.baseline.copy(
                typicalSleepHours = typicalSleep,
                typicalMood = typicalMood.roundToInt(),
                familyHistory = familyHistory.split(",").map { it.trim() }.filter { it.isNotEmpty() },
            ),
        )
        onFinished()
    }

    ScreenColumn(title = "Welcome to ITER", subtitle = "Step ${step + 1} of $totalSteps. You can skip anything that isn't marked required.", onBack = onBack) {
        LinearProgressIndicator(progress = { (step + 1) / totalSteps.toFloat() }, modifier = Modifier.fillMaxWidth())

        when (step) {
            0 -> SectionCard(title = "Your journey, one day at a time") {
                Text("Each day you'll answer a few short questions about how you feel. Your clinician sees the pattern, not just one appointment.")
                Text("This first part takes about 5 minutes and helps us understand what's normal for you.")
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("What should we call you?") }, modifier = Modifier.fillMaxWidth())
            }
            1 -> SectionCard(title = "Your treatment") {
                Text("Why we ask: so your answers can be matched to the medication you're taking.", style = MaterialTheme.typography.bodySmall)
                OutlinedTextField(value = medication, onValueChange = { medication = it }, label = { Text("Medication (required)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = dose, onValueChange = { dose = it }, label = { Text("Dose") }, modifier = Modifier.fillMaxWidth())
            }
            2 -> SectionCard(title = "What's typical for you") {
                Text("Why we ask: 6 hours of sleep means different things for different people.", style = MaterialTheme.typography.bodySmall)
                ScaleQuestion("Usual hours of sleep", typicalSleep, { typicalSleep = (it * 2).roundToInt() / 2f }, "0", "12+", max = 12f, steps = 23, valueText = "$typicalSleep h")
                ScaleQuestion("Your mood on a typical good day", typicalMood, { typicalMood = it }, "Very low", "Very good")
            }
            else -> SectionCard(title = "Family history (optional)") {
                Text("Why we ask: some conditions and medication responses run in families. Skip if you prefer.", style = MaterialTheme.typography.bodySmall)
                OutlinedTextField(value = familyHistory, onValueChange = { familyHistory = it }, label = { Text("e.g. Parent: depression") }, modifier = Modifier.fillMaxWidth())
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (step > 0) OutlinedButton(onClick = { step-- }, modifier = Modifier.weight(1f)) { Text("Back") }
            Button(
                onClick = { if (step < totalSteps - 1) step++ else finish() },
                enabled = step != 1 || medication.isNotBlank(),
                modifier = Modifier.weight(1f),
            ) { Text(if (step < totalSteps - 1) "Next" else "Finish") }
        }
    }
}
