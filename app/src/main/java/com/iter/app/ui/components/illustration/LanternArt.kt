package com.iter.app.ui.components.illustration

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import com.iter.app.ui.theme.Brand

/**
 * The ITER lantern. [brightness] 0f = small ember, 1f = brightest. Drawn in code so it scales cleanly.
 * The lantern takes ~60% of the box so the glow stays inside it.
 */
@Composable
fun LanternArt(brightness: Float, modifier: Modifier = Modifier, glow: Boolean = true) {
    Canvas(modifier) {
        drawLantern(Offset(size.width / 2, size.height / 2), size.minDimension * 0.6f, brightness, glow, maxGlowRadius = size.minDimension / 2)
    }
}

/**
 * Draws a lantern centered at [center], [h] tall (width is about 0.6 h).
 * Shared by the logo and the bear illustration.
 */
fun DrawScope.drawLantern(
    center: Offset,
    h: Float,
    brightness: Float,
    glow: Boolean = true,
    maxGlowRadius: Float = Float.MAX_VALUE,
) {
    val b = brightness.coerceIn(0f, 1f)
    val w = h * 0.52f
    val top = center.y - h / 2
    val metal = Brand.LanternMetal
    val stroke = h * 0.045f

    // Glow behind the lantern grows with brightness; a faint warmth even at level 0.
    if (glow) {
        val radius = (h * (0.45f + 0.75f * b)).coerceAtMost(maxGlowRadius)
        drawCircle(
            Brush.radialGradient(
                listOf(Brand.LanternGlow.copy(alpha = 0.25f + 0.55f * b), Color.Transparent),
                center = center,
                radius = radius,
            ),
            radius = radius,
            center = center,
        )
    }

    // Handle ring
    drawArc(
        metal, startAngle = 180f, sweepAngle = 180f, useCenter = false,
        topLeft = Offset(center.x - w * 0.22f, top), size = Size(w * 0.44f, h * 0.2f),
        style = Stroke(stroke, cap = StrokeCap.Round),
    )
    // Cap (trapezoid)
    val capTop = top + h * 0.1f
    val capBottom = top + h * 0.24f
    drawPath(
        Path().apply {
            moveTo(center.x - w * 0.22f, capTop)
            lineTo(center.x + w * 0.22f, capTop)
            lineTo(center.x + w * 0.5f, capBottom)
            lineTo(center.x - w * 0.5f, capBottom)
            close()
        },
        metal,
    )
    // Glass with warm light
    val glassTop = capBottom
    val glassBottom = top + h * 0.84f
    val glassColor = lerp(Color(0xFF6E6A52), Brand.LanternLight, 0.35f + 0.65f * b)
    drawRoundRect(
        glassColor,
        topLeft = Offset(center.x - w * 0.42f, glassTop),
        size = Size(w * 0.84f, glassBottom - glassTop),
        cornerRadius = CornerRadius(w * 0.12f),
    )
    // Flame: a soft teardrop that grows with brightness
    val flameH = (glassBottom - glassTop) * (0.28f + 0.5f * b)
    val flameBase = glassBottom - (glassBottom - glassTop) * 0.18f
    val flameW = w * (0.16f + 0.18f * b)
    drawPath(
        Path().apply {
            moveTo(center.x, flameBase - flameH)
            cubicTo(center.x + flameW, flameBase - flameH * 0.45f, center.x + flameW, flameBase, center.x, flameBase)
            cubicTo(center.x - flameW, flameBase, center.x - flameW, flameBase - flameH * 0.45f, center.x, flameBase - flameH)
            close()
        },
        lerp(Color(0xFFE9A94F), Color(0xFFFFF4D6), b),
    )
    // Frame bars
    listOf(-0.42f, 0.42f).forEach { f ->
        drawLine(metal, Offset(center.x + w * f, glassTop), Offset(center.x + w * f, glassBottom), strokeWidth = stroke)
    }
    // Base
    drawRoundRect(
        metal,
        topLeft = Offset(center.x - w * 0.5f, glassBottom),
        size = Size(w, h * 0.1f),
        cornerRadius = CornerRadius(h * 0.03f),
    )
}
