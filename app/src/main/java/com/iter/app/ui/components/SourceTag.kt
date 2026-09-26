package com.iter.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iter.app.data.model.DataSource
import com.iter.app.ui.theme.Brand

/** Small label saying where data came from. Not a pill: pills are reserved (spec section 6). */
@Composable
fun SourceTag(source: DataSource) {
    val ink = Color(0xFF10130E)
    val (bg, fg) = when (source) {
        DataSource.Patient -> Brand.Mist to ink
        DataSource.Supporter -> Brand.Sage.copy(alpha = 0.18f) to Brand.SageText
        DataSource.Ai -> Brand.ChartSand.copy(alpha = 0.22f) to Color(0xFF6B5330)
        DataSource.Device, DataSource.Clinician -> Brand.ChartSlate.copy(alpha = 0.18f) to Color(0xFF3E4F61)
    }
    Text(
        text = source.label,
        style = MaterialTheme.typography.labelSmall,
        color = fg,
        modifier = Modifier
            .background(bg, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp),
    )
}
