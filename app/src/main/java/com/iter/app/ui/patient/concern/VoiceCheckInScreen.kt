package com.iter.app.ui.patient.concern

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.iter.app.ui.components.ScreenColumn
import com.iter.app.ui.components.SectionCard
import com.iter.app.ui.components.buttons.HelplineLink
import com.iter.app.ui.components.buttons.PrimaryButton
import com.iter.app.ui.theme.IterTheme
import kotlinx.coroutines.delay

private const val SECONDS = 30

/**
 * The 30-second "Talk it through" moment. Demo: text prompts only.
 * TODO(stretch): voice via ElevenLabs from a server (see voice/VoiceCheckIn.kt).
 * Always says it's automated; never claims to be a clinician or emergency care.
 */
@Composable
fun VoiceCheckInScreen(onDone: () -> Unit) {
    val context = LocalContext.current
    var left by remember { mutableIntStateOf(SECONDS) }
    LaunchedEffect(Unit) {
        while (left > 0) {
            delay(1000)
            left--
        }
    }
    val prompt = when {
        left > 20 -> "Let's slow down together. Breathe in gently through your nose."
        left > 10 -> "And out, slowly. Notice where you're sitting, and what you can hear."
        left > 0 -> "What's one small thing that might help in the next hour?"
        else -> "Thank you for taking this moment. It's okay to ask someone for help."
    }

    ScreenColumn(title = "Talk it through", subtitle = "An automated guide, not a clinician. ${if (left > 0) "$left seconds" else "Done"}") {
        SectionCard { Text(prompt, style = MaterialTheme.typography.headlineSmall, color = IterTheme.chrome.ink) }
        PrimaryButton(if (left > 0) "End early" else "Done", onDone, Modifier.fillMaxWidth())
        HelplineLink("In crisis right now? Call the 988 Lifeline", {
            context.startActivity(Intent(Intent.ACTION_DIAL, "tel:988".toUri()))
        })
    }
}
