package com.iter.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.iter.app.ui.components.buttons.SecondaryButton
import com.iter.app.ui.components.icons.IterIcons
import com.iter.app.ui.theme.IterTheme

/** Small "i" button; tapping it pops up "Why are we asking you this?" with [explanation]. */
@Composable
fun WhyWeAskInfo(explanation: String, modifier: Modifier = Modifier) {
    val chrome = IterTheme.chrome
    var open by remember { mutableStateOf(false) }
    Box(
        modifier
            .size(48.dp) // touch target
            .clip(CircleShape)
            .clickable(role = Role.Button, onClickLabel = "Why are we asking you this?") { open = true },
        contentAlignment = Alignment.Center,
    ) {
        Icon(IterIcons.Info, contentDescription = "Why are we asking you this?", tint = chrome.brandText, modifier = Modifier.size(22.dp))
    }
    if (open) {
        Dialog(onDismissRequest = { open = false }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .background(chrome.page, RoundedCornerShape(14.dp))
                    .border(1.dp, chrome.hairline, RoundedCornerShape(14.dp))
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text("Why are we asking you this?", style = MaterialTheme.typography.titleLarge, color = chrome.ink)
                Text(explanation, style = MaterialTheme.typography.bodyMedium, color = chrome.charcoal)
                SecondaryButton("Got it", { open = false }, Modifier.fillMaxWidth())
            }
        }
    }
}
