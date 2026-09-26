package com.iter.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iter.app.data.model.DataSource

/** Small label saying where data came from: patient, supporter, AI, etc. */
@Composable
fun SourceTag(source: DataSource) {
    val colors = MaterialTheme.colorScheme
    val (bg, fg) = when (source) {
        DataSource.Patient -> colors.primaryContainer to colors.onPrimaryContainer
        DataSource.Supporter -> colors.secondaryContainer to colors.onSecondaryContainer
        DataSource.Ai -> colors.tertiaryContainer to colors.onSurface
        DataSource.Device, DataSource.Clinician -> colors.surfaceVariant to colors.onSurfaceVariant
    }
    Text(
        text = source.label,
        style = MaterialTheme.typography.labelSmall,
        color = fg,
        modifier = Modifier
            .background(bg, RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 3.dp),
    )
}
