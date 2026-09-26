package com.iter.app.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.ButtonText

/**
 * Text-only link-style button. Underline sits 3dp below the text.
 * [alwaysUnderlined] is for the helpline link; otherwise the underline shows on hover/press.
 */
@Composable
private fun TextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    color: Color,
    style: TextStyle,
    alwaysUnderlined: Boolean,
) {
    val interaction = remember { MutableInteractionSource() }
    val hovered by interaction.collectIsHoveredAsState()
    val pressed by interaction.collectIsPressedAsState()
    val textColor = if (enabled) color else Color(0xFF2E2E2E).copy(alpha = 0.4f)
    val underline by animateColorAsState(
        if (enabled && (alwaysUnderlined || hovered || pressed)) textColor else Color.Transparent,
        tween(150),
        label = "underline",
    )
    Text(
        text = text,
        style = style,
        color = textColor,
        modifier = modifier
            .minimumInteractiveComponentSize() // 48dp touch target without changing the look
            .hoverable(interaction, enabled)
            .clickable(interaction, indication = null, enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(horizontal = 4.dp)
            .drawBehind {
                val y = size.height + 3.dp.toPx()
                drawLine(underline, Offset(0f, y), Offset(size.width, y), strokeWidth = 1.dp.toPx())
            },
    )
}

/** C) Low-emphasis actions: Back, Why we ask this, Not today, Log in instead. */
@Composable
fun TertiaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) = TextLink(text, onClick, modifier, enabled, Brand.SagePressed, ButtonText.Medium, alwaysUnderlined = false)

/** I-2) Small, always-visible crisis link ("In crisis right now? Call the 988 Lifeline"). */
@Composable
fun HelplineLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = TextLink(
    text, onClick, modifier, enabled = true, Brand.TerracottaHover,
    ButtonText.Medium.copy(fontSize = 13.8.sp), // 0.86rem
    alwaysUnderlined = true,
)
