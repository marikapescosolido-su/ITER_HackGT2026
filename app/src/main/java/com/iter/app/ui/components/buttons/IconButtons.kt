package com.iter.app.ui.components.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.iter.app.ui.theme.Brand

private val Ink = Color(0xFF10130E)

/** E) 44dp circular icon button (Home dashboard: notifications, sharing, account). */
@Composable
fun IconCircleButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interaction = remember { MutableInteractionSource() }
    val hovered by interaction.collectIsHoveredAsState()
    val pressed by interaction.collectIsPressedAsState()
    val bg by animateColorAsState(if (hovered || pressed) Brand.MistHover else Brand.Mist, tween(150), label = "bg")
    Box(
        modifier
            .offset(y = if (pressed) 1.dp else 0.dp)
            .size(44.dp)
            .clip(CircleShape)
            .background(bg)
            .hoverable(interaction)
            .clickable(interaction, indication = null, role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = contentDescription, tint = Ink, modifier = Modifier.size(19.dp))
    }
}

/** F) Mist button with icon + label. Siblings share the same weight so none looks more important. */
@Composable
fun UtilityButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) = ButtonBase(
    text, onClick, modifier, enabled, RoundedCornerShape(11.dp), ButtonSize.Medium.minHeight,
    14.dp, ButtonSize.Medium.text,
    normal = ButtonColors(Brand.Mist, Ink),
    hovered = ButtonColors(Brand.MistHover, Ink),
    pressed = ButtonColors(Brand.MistHover, Ink),
    icon = icon,
    iconSize = 16.dp,
)
