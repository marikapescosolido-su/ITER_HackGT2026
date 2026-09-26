package com.iter.app.ui.patient.onboarding

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.PrimaryButton

@Composable
fun MedicationStep(state: OnboardingState, onContinue: () -> Unit) {
    Text("Which medication is your care team monitoring?", style = MaterialTheme.typography.bodyLarge)
    WhyWeAsk("Different medications have different side effects. We match a few of your daily questions to yours, and your clinician sees your full list so nothing is read in isolation.")
    SectionCard(title = "Monitored medication") {
        OutlinedTextField(state.medication, { state.medication = it }, label = { Text("Name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(state.dose, { state.dose = it }, label = { Text("Dose") }, singleLine = true, modifier = Modifier.fillMaxWidth())
    }
    SectionCard(title = "Anything else you take") {
        OutlinedTextField(
            state.otherMedications, { state.otherMedications = it },
            label = { Text("Other medicines or supplements") },
            modifier = Modifier.fillMaxWidth(),
        )
    }
    PrimaryButton("Continue", onContinue, Modifier.fillMaxWidth(), enabled = state.medicationAnswered)
}
