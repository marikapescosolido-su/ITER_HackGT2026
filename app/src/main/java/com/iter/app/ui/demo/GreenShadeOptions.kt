package com.iter.app.ui.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.IterTheme
import com.iter.app.ui.theme.LightChrome

/** Candidate replacements for Brand.Sage, from the current sage to brighter greens. Pick one, then delete this. */
private val GreenOptions = listOf(
    "Old Sage" to Color(0xFF757F64),
    "1 Bright Sage" to Color(0xFF7E9163),
    "2 Olive Leaf" to Color(0xFF7F9A55),
    "3 Moss" to Color(0xFF6F9B4E),
    "4 Fern (in use)" to Brand.Sage,
    "5 Leaf" to Color(0xFF58A85A),
    "6 Spring" to Color(0xFF6BBF5E),
    "7 Meadow" to Color(0xFF86C96E),
)

private val SwatchShape = RoundedCornerShape(12.dp)

/** WCAG contrast ratio; 4.5 or more is readable for normal-size text. */
private fun contrast(a: Color, b: Color): Float {
    val (hi, lo) = listOf(a.luminance(), b.luminance()).sortedDescending()
    return (hi + 0.05f) / (lo + 0.05f)
}

private fun Color.hex() = "#%06X".format(toArgb() and 0xFFFFFF)

@Composable
fun GreenShadeOptions() {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(
            "Readable = 4.5 or more. Light text is the cream used on buttons now.",
            style = MaterialTheme.typography.bodySmall,
            color = IterTheme.chrome.charcoal,
        )
        GreenOptions.forEach { (name, green) -> ShadeRow(name, green) }
    }
}

@Composable
private fun ShadeRow(name: String, green: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(Modifier.size(28.dp).background(green, RoundedCornerShape(8.dp)))
            Text(name, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
            Text(green.hex(), style = MaterialTheme.typography.labelMedium, color = IterTheme.chrome.charcoal)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SampleButton("Continue", green, Brand.Cream, Modifier.weight(1f))
            SampleButton("Continue", green, LightChrome.ink, Modifier.weight(1f))
        }
    }
}

@Composable
private fun SampleButton(label: String, green: Color, textColor: Color, modifier: Modifier) {
    val ratio = contrast(green, textColor)
    val which = if (textColor == Brand.Cream) "Light text" else "Dark text"
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier.fillMaxWidth().height(48.dp).background(green, SwatchShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(label, style = MaterialTheme.typography.labelLarge, color = textColor)
        }
        Text(
            "$which %.1f%s".format(ratio, if (ratio >= 4.5f) " ✓" else ""),
            style = MaterialTheme.typography.labelSmall,
            color = IterTheme.chrome.charcoal,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}
