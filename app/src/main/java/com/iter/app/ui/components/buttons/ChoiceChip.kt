package com.iter.app.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.ButtonText
import com.iter.app.ui.theme.IterTheme

private val ChipShape = RoundedCornerShape(9.dp)

/**
 * Answer chip for survey options (side effects, PHQ-9 answers, Yes / Not today).
 * Selected = sage fill + cream text, same as the selected segment. 9dp radius, not a pill.
 */
@Composable
fun ChoiceChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val chrome = IterTheme.chrome
    val bg by animateColorAsState(if (selected) Brand.Sage else Color.Transparent, tween(150), label = "chip")
    Text(
        text = text,
        style = ButtonText.Small,
        color = if (selected) Brand.Cream else chrome.ink,
        modifier = modifier
            .defaultMinSize(minHeight = 36.dp)
            .clip(ChipShape)
            .background(bg, ChipShape)
            .border(1.dp, if (selected) Brand.Sage else chrome.hairline, ChipShape)
            .toggleable(value = selected, role = Role.Checkbox, onValueChange = { onClick() })
            .padding(horizontal = 14.dp, vertical = 9.dp),
    )
}
