package com.iter.app.ui.patient.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.ChoiceChip
import com.iter.app.ui.components.buttons.PrimaryButton

/** No skip on this step: answers personalize the daily survey. "None that I know of" is a valid answer. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FamilyHistoryStep(state: OnboardingState, onContinue: () -> Unit) {
    Text("Has anyone in your close family (parents, siblings, grandparents) had any of these?", style = MaterialTheme.typography.bodyLarge)
    WhyWeAsk("Some conditions and medication responses run in families. This helps us choose which questions to ask you, and gives your clinician context.")
    SectionCard {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OnboardingState.conditions.forEach { condition ->
                val selected = condition in state.familyConditions
                ChoiceChip(condition, selected, {
                    when {
                        selected -> state.familyConditions.remove(condition)
                        condition == OnboardingState.NONE_KNOWN -> {
                            state.familyConditions.clear()
                            state.familyConditions.add(condition)
                        }
                        else -> {
                            state.familyConditions.remove(OnboardingState.NONE_KNOWN)
                            state.familyConditions.add(condition)
                        }
                    }
                })
            }
        }
        OutlinedTextField(
            state.familyNotes, { state.familyNotes = it },
            label = { Text("Anything else? (optional)") },
            placeholder = { Text("e.g. Parent: depression") },
            modifier = Modifier.fillMaxWidth(),
        )
    }
    PrimaryButton("Continue", onContinue, Modifier.fillMaxWidth(), enabled = state.familyAnswered)
}
