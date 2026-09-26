package com.iter.app.ui.patient.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.iter.app.domain.ReportSchedule
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ChoiceChip
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.theme.IterTheme

/**
 * Who receives the report and how often. Decided together with the clinician at the appointment;
 * after that the PDF is emailed automatically. The patient never sees the report itself.
 */
@Composable
fun CareTeamStep(state: OnboardingState, onContinue: () -> Unit) {
    Text("Who should receive your reports?", style = MaterialTheme.typography.bodyLarge)
    WhyWeAsk("Your clinician gets a short report on a schedule you agree on together. Only your care team sees your answers.")
    SectionCard(title = "Your clinician") {
        OutlinedTextField(state.clinicianName, { state.clinicianName = it }, label = { Text("Name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            state.clinicianEmail, { state.clinicianEmail = it }, label = { Text("Email") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
        )
    }
    SectionCard(title = "Send a report every") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ReportSchedule.options.forEach { days ->
                ChoiceChip(ReportSchedule.chipLabel(days), state.reportEveryDays == days, { state.reportEveryDays = days })
            }
        }
        Text("Choose this together with your clinician.", style = MaterialTheme.typography.bodySmall, color = IterTheme.chrome.charcoal)
    }
    PrimaryButton("Continue", onContinue, Modifier.fillMaxWidth(), enabled = state.careTeamAnswered)
}
