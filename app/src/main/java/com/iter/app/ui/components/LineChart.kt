package com.iter.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp

data class ChartSeries(val label: String, val values: List<Float?>, val color: Color)

/**
 * Simple multi-line chart. Null values are gaps (missing data is never filled in).
 * [markerIndex] draws a dashed vertical line, e.g. at a dose change.
 */
@Composable
fun LineChart(
    series: List<ChartSeries>,
    yMax: Float,
    modifier: Modifier = Modifier,
    startLabel: String? = null,
    endLabel: String? = null,
    markerIndex: Int? = null,
    markerLabel: String? = null,
) {
    val gridColor = MaterialTheme.colorScheme.outlineVariant
    val markerColor = MaterialTheme.colorScheme.onSurfaceVariant
    Column(modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Canvas(Modifier.fillMaxWidth().height(160.dp)) {
            val count = series.maxOfOrNull { it.values.size } ?: 0
            if (count < 2) return@Canvas
            val stepX = size.width / (count - 1)
            fun y(v: Float) = size.height - (v / yMax) * size.height

            for (i in 0..4) {
                val gy = size.height * i / 4
                drawLine(gridColor, Offset(0f, gy), Offset(size.width, gy), strokeWidth = 1f)
            }
            if (markerIndex != null && markerIndex in 0 until count) {
                val mx = markerIndex * stepX
                drawLine(
                    markerColor, Offset(mx, 0f), Offset(mx, size.height), strokeWidth = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f)),
                )
            }
            for (s in series) {
                var prev: Offset? = null
                s.values.forEachIndexed { i, v ->
                    if (v == null) {
                        prev = null
                    } else {
                        val point = Offset(i * stepX, y(v))
                        prev?.let { drawLine(s.color, it, point, strokeWidth = 5f) }
                        drawCircle(s.color, radius = 5f, center = point)
                        prev = point
                    }
                }
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(startLabel.orEmpty(), style = MaterialTheme.typography.labelSmall)
            if (markerLabel != null) Text("┆ $markerLabel", style = MaterialTheme.typography.labelSmall)
            Text(endLabel.orEmpty(), style = MaterialTheme.typography.labelSmall)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            series.forEach { s ->
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(Modifier.size(10.dp).background(s.color, CircleShape))
                    Text(s.label, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
