package com.iter.app.ui.components.buttons

import android.provider.Settings
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.ButtonText

private const val PULSE_MS = 2600

/**
 * H) "Talk it through — 30 sec" on the Concern Alert overlay only.
 * The ONLY non-user-triggered motion in the app. When the system has animations turned off
 * (Android's version of prefers-reduced-motion), the ring is removed entirely.
 */
@Composable
fun SupportPulseButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    val context = LocalContext.current
    val reduceMotion = remember {
        Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) == 0f
    }
    val ringOffset = 6.dp

    val ringModifier = if (reduceMotion) {
        Modifier
    } else {
        val transition = rememberInfiniteTransition(label = "pulse")
        // Keyframes match the CSS (ease-out): 0% scale .96 / opacity .5, 70% scale 1.14 / opacity 0, 100% opacity 0.
        val scale by transition.animateFloat(
            initialValue = 0.96f,
            targetValue = 1.14f,
            animationSpec = infiniteRepeatable(
                keyframes {
                    durationMillis = PULSE_MS
                    0.96f at 0 using EaseOut
                    1.14f at (PULSE_MS * 0.7).toInt()
                    1.14f at PULSE_MS
                },
                RepeatMode.Restart,
            ),
            label = "scale",
        )
        val alpha by transition.animateFloat(
            initialValue = 0.5f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                keyframes {
                    durationMillis = PULSE_MS
                    0.5f at 0 using EaseOut
                    0f at (PULSE_MS * 0.7).toInt()
                    0f at PULSE_MS
                },
                RepeatMode.Restart,
            ),
            label = "alpha",
        )
        Modifier.drawBehind {
            val grow = ringOffset.toPx()
            val ringSize = Size(size.width + grow * 2, size.height + grow * 2)
            scale(scale) {
                drawRoundRect(
                    color = Brand.Sage.copy(alpha = alpha),
                    topLeft = Offset(-grow, -grow),
                    size = ringSize,
                    cornerRadius = CornerRadius(ringSize.height / 2),
                    style = Stroke(width = 1.5.dp.toPx()),
                )
            }
        }
    }

    Box(modifier.padding(ringOffset)) {
        ButtonBase(
            text, onClick, ringModifier, enabled = true, RoundedCornerShape(999.dp), 52.dp, 26.dp, ButtonText.Large,
            normal = ButtonColors(Brand.Sage, Brand.OnSage),
            hovered = ButtonColors(Brand.SageHover, Brand.OnSage),
            pressed = ButtonColors(Brand.SagePressed, Brand.OnSage),
            icon = icon,
            iconSize = 17.dp,
        )
    }
}
