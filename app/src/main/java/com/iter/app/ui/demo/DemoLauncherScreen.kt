package com.iter.app.ui.demo

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iter.app.data.DemoRepository
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.illustration.LanternArt
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import com.iter.app.ui.navigation.Routes

/** Demo-only start screen: pretend to be each person's phone. */
@Composable
fun DemoLauncherScreen(onOpen: (String) -> Unit) {
    val patient = DemoRepository.patient
    ScreenColumn(
        title = "ITER",
        subtitle = "Your treatment journey, one day at a time. Choose whose phone to show:",
        topBar = { LanternArt(brightness = 1f, modifier = Modifier.size(120.dp)) },
    ) {
        LauncherCard("Patient", "${patient.name}'s phone: daily check-in, streak, sharing") { onOpen(Routes.PATIENT_HOME) }
        LauncherCard("Supporter", "Sam's phone (partner): updates, nudges, weekly observation") { onOpen(Routes.SUPPORTER_HOME) }
        LauncherCard("New patient", "Onboarding and baseline assessment") { onOpen(Routes.ONBOARDING) }
        LauncherCard("Concern alert", "Patient home as if today's answers suggested a hard day") {
            DemoRepository.concernPending = true
            onOpen(Routes.PATIENT_HOME)
        }
        LauncherCard("Design system", "All buttons and states, for checking against the spec") { onOpen(Routes.DESIGN) }
    }
}

@Composable
private fun LauncherCard(title: String, detail: String, onClick: () -> Unit) {
    androidx.compose.foundation.layout.Box(Modifier.clickable(onClick = onClick)) {
        SectionCard(title = title) {
            Text(detail, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
