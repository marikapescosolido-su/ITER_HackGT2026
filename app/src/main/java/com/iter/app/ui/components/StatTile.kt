package com.iter.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.iter.app.ui.theme.IterTheme
import com.iter.app.ui.theme.Sora

@Composable
fun StatTile(label: String, value: String, detail: String? = null, modifier: Modifier = Modifier) {
    val chrome = IterTheme.chrome
    Column(
        modifier
            .background(chrome.panel, PanelShape)
            .border(1.dp, chrome.hairline, PanelShape)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = chrome.charcoal)
        Text(value, style = MaterialTheme.typography.headlineSmall.copy(fontFamily = Sora, fontWeight = FontWeight.SemiBold), color = chrome.ink)
        if (detail != null) Text(detail, style = MaterialTheme.typography.labelSmall, color = chrome.charcoal)
    }
}
