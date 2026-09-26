package com.iter.app.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.ButtonText

/** Button sizes from the spec's type scale. */
enum class ButtonSize(val minHeight: Dp, val radius: Dp, val text: TextStyle, val horizontalPadding: Dp) {
    Large(56.dp, 14.dp, ButtonText.Large, 24.dp),
    Medium(44.dp, 11.dp, ButtonText.Medium, 18.dp),
    Small(36.dp, 9.dp, ButtonText.Small, 14.dp),
}

/** Colors for one visual state of a button. */
data class ButtonColors(val container: Color, val content: Color, val border: Color? = null)

/** Disabled look shared by every button type (spec K). */
val DisabledColors = ButtonColors(
    container = Brand.Mist,
    content = Color(0xFF2E2E2E).copy(alpha = 0.55f),
    border = Brand.Mist,
)

private const val FADE_MS = 150

/**
 * Shared behavior for all buttons: 0.15s color fade on hover/press, 1px press-down,
 * disabled state. Visual differences are passed in as colors per state.
 */
@Composable
internal fun ButtonBase(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    shape: Shape,
    minHeight: Dp,
    horizontalPadding: Dp,
    textStyle: TextStyle,
    normal: ButtonColors,
    hovered: ButtonColors,
    pressed: ButtonColors,
    icon: ImageVector? = null,
    iconSize: Dp = 16.dp,
    borderWidth: Dp = 1.5.dp,
) {
    val interaction = remember { MutableInteractionSource() }
    val isHovered by interaction.collectIsHoveredAsState()
    val isPressed by interaction.collectIsPressedAsState()
    val target = when {
        !enabled -> DisabledColors
        isPressed -> pressed
        isHovered -> hovered
        else -> normal
    }
    val container by animateColorAsState(target.container, tween(FADE_MS), label = "container")
    val content by animateColorAsState(target.content, tween(FADE_MS), label = "content")
    val borderColor = target.border ?: normal.border

    Row(
        modifier = modifier
            .offset(y = if (isPressed && enabled) 1.dp else 0.dp)
            .defaultMinSize(minHeight = minHeight)
            .clip(shape)
            .background(container, shape)
            .then(if (borderColor != null) Modifier.border(BorderStroke(borderWidth, borderColor), shape) else Modifier)
            .hoverable(interaction, enabled)
            .clickable(interaction, indication = null, enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(horizontal = horizontalPadding, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) Icon(icon, contentDescription = null, tint = content, modifier = Modifier.size(iconSize))
        Text(text, style = textStyle, color = content, textAlign = TextAlign.Center)
    }
}

internal fun sizeShape(size: ButtonSize) = RoundedCornerShape(size.radius)
