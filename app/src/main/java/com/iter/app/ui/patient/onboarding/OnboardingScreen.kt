package com.iter.app.ui.patient.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.theme.IterTheme

/**
 * Wireflow: Sign up -> Family history -> Medication setup -> Care team -> Support circle.
 * Only the support circle step can be skipped (family history feeds survey personalization).
 */
@Composable
fun OnboardingScreen(onBack: () -> Unit, onFinished: () -> Unit) {
    val state = remember { OnboardingState() }
    var step by remember { mutableIntStateOf(0) }
    val steps = 5
    val back: () -> Unit = { if (step == 0) onBack() else step-- }

    ScreenColumn(
        title = listOf("Welcome to ITER", "Family history", "Your medication", "Your care team", "Your support circle")[step],
        subtitle = if (step == 0) null else "Step $step of ${steps - 1}",
        onBack = back,
    ) {
        if (step > 0) {
            LinearProgressIndicator(
                progress = { step / (steps - 1f) },
                modifier = Modifier.fillMaxWidth(),
                trackColor = IterTheme.chrome.hairline,
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            when (step) {
                0 -> SignUpStep(state, onCreate = { step = 1 }, onLogIn = onFinished)
                1 -> FamilyHistoryStep(state, onContinue = { step = 2 })
                2 -> MedicationStep(state, onContinue = { step = 3 })
                3 -> CareTeamStep(state, onContinue = { step = 4 })
                else -> SupportCircleStep(
                    state,
                    onInvite = { state.save(invite = true); onFinished() },
                    onSkip = { state.save(invite = false); onFinished() },
                )
            }
        }
    }
}
