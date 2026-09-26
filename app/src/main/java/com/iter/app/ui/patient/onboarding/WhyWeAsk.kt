package com.iter.app.ui.patient.onboarding

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.iter.app.ui.components.buttons.TertiaryButton
import com.iter.app.ui.theme.IterTheme

/** Tertiary "Why we ask this" that reveals a short explanation. */
@Composable
fun WhyWeAsk(explanation: String) {
    var open by remember { mutableStateOf(false) }
    TertiaryButton(if (open) "Hide" else "Why we ask this", { open = !open })
    if (open) Text(explanation, style = MaterialTheme.typography.bodyMedium, color = IterTheme.chrome.charcoal)
}
