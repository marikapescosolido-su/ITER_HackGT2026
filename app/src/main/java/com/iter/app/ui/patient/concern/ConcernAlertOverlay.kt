package com.iter.app.ui.patient.concern

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import com.iter.app.ui.components.buttons.HelplineLink
import com.iter.app.ui.components.buttons.SecondaryButton
import com.iter.app.ui.components.buttons.SupportPulseButton
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.theme.IterTheme

/**
 * Wireflow "Concern Alert Overlay": Secondary "I'm okay" + pulsing "Talk it through — 30 sec",
 * with the small helpline link always visible underneath. Calm, never alarming.
 */
@Composable
fun ConcernAlertOverlay(onTalk: () -> Unit, onOkay: () -> Unit) {
    val context = LocalContext.current
    val chrome = IterTheme.chrome
    Dialog(onDismissRequest = onOkay, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(chrome.page, RoundedCornerShape(14.dp))
                .border(1.dp, chrome.hairline, RoundedCornerShape(14.dp))
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Today might feel heavy", style = MaterialTheme.typography.headlineSmall, color = chrome.ink)
            Text(
                "You don't have to explain anything. If it would help, a short guided moment is here.",
                style = MaterialTheme.typography.bodyMedium,
                color = chrome.charcoal,
            )
            SupportPulseButton("Talk it through — 30 sec", onTalk, icon = IterIcons.Phone)
            SecondaryButton("I'm okay", onOkay, Modifier.fillMaxWidth())
            HelplineLink("In crisis right now? Call the 988 Lifeline", {
                context.startActivity(Intent(Intent.ACTION_DIAL, "tel:988".toUri()))
            })
        }
    }
}
