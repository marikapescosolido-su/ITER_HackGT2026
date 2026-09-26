package com.iter.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * A prompt with a 0..max slider and words at each end.
 * [value] null = not answered yet: no thumb and no number, so nothing looks preselected.
 * [whyWeAsk] adds an "i" button next to the prompt that pops up the explanation.
 */
@Composable
fun ScaleQuestion(
    prompt: String,
    value: Float?,
    onValueChange: (Float) -> Unit,
    lowLabel: String,
    highLabel: String,
    max: Float = 10f,
    steps: Int = 9,
    valueText: String = value?.roundToInt()?.toString().orEmpty(),
    whyWeAsk: String? = null,
) {
    val answered = value != null
    val colors = if (answered) {
        SliderDefaults.colors()
    } else {
        val track = SliderDefaults.colors().inactiveTrackColor
        SliderDefaults.colors(thumbColor = Color.Transparent, activeTrackColor = track, inactiveTrackColor = track)
    }
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
            Text(prompt, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
            if (whyWeAsk != null) WhyWeAskInfo(whyWeAsk)
        }
        Text(
            if (answered) valueText else "Move the slider",
            style = if (answered) MaterialTheme.typography.titleMedium else MaterialTheme.typography.labelMedium,
            color = if (answered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Slider(value = value ?: 0f, onValueChange = onValueChange, valueRange = 0f..max, steps = steps, colors = colors)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(lowLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
            Text(
                highLabel,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
