package com.iter.app.ui.components.illustration

import android.provider.Settings
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.iter.app.domain.Lantern
import com.iter.app.ui.theme.Brand
import com.iter.app.ui.theme.IterTheme

/**
 * The journey header: a bear carrying the lantern along a path with one stone per day of the week.
 * The bear stands at this week's lantern level; the lantern glows brighter as the level rises.
 * The bear only moves when the level changes (after a check-in), never on its own.
 */
@Composable
fun BearPath(level: Int, modifier: Modifier = Modifier, fromLevel: Int? = null) {
    val context = LocalContext.current
    val reduceMotion = remember {
        Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) == 0f
    }
    fun fraction(l: Int) = l.coerceIn(0, Lantern.MAX) / Lantern.MAX.toFloat()
    // Starts at [fromLevel] (e.g. before today's check-in) and walks to [level]. Only moves on a change.
    val anim = remember { Animatable(fraction(fromLevel ?: level)) }
    LaunchedEffect(level) {
        if (reduceMotion) anim.snapTo(fraction(level)) else anim.animateTo(fraction(level), tween(1400, delayMillis = 400))
    }
    val progress = anim.value
    val chrome = IterTheme.chrome

    Canvas(
        modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(10.dp))
            .semantics { contentDescription = "Your lantern this week: level $level of ${Lantern.MAX}" },
    ) {
        drawScenery(chrome.panel)
        val path = PathCurve(size)
        drawTrail(path, level)
        val pos = path.point(0.04f + 0.9f * progress)
        drawBear(Offset(pos.x, pos.y + size.height * 0.02f), size.height * 0.46f, progress)
    }
}

/** Cubic curve for the trail, from bottom-left to the hills on the right. */
private class PathCurve(size: Size) {
    val p0 = Offset(size.width * 0.02f, size.height * 0.9f)
    val p1 = Offset(size.width * 0.35f, size.height * 0.95f)
    val p2 = Offset(size.width * 0.55f, size.height * 0.55f)
    val p3 = Offset(size.width * 0.98f, size.height * 0.62f)

    fun point(t: Float): Offset {
        val u = 1 - t
        return p0 * (u * u * u) + p1 * (3 * u * u * t) + p2 * (3 * u * t * t) + p3 * (t * t * t)
    }

    fun toPath() = Path().apply {
        moveTo(p0.x, p0.y)
        cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y)
    }
}

private fun DrawScope.drawScenery(panel: Color) {
    // Sky: soft sage wash over the panel color
    drawRect(Brush.verticalGradient(listOf(Brand.Mist.copy(alpha = 0.55f), panel)))
    // Far hill
    drawPath(
        Path().apply {
            moveTo(0f, size.height * 0.62f)
            cubicTo(size.width * 0.25f, size.height * 0.30f, size.width * 0.45f, size.height * 0.42f, size.width * 0.62f, size.height * 0.5f)
            cubicTo(size.width * 0.78f, size.height * 0.36f, size.width * 0.9f, size.height * 0.3f, size.width, size.height * 0.38f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        },
        Brand.Sage.copy(alpha = 0.35f),
    )
    // Near hill
    drawPath(
        Path().apply {
            moveTo(0f, size.height * 0.78f)
            cubicTo(size.width * 0.3f, size.height * 0.6f, size.width * 0.6f, size.height * 0.7f, size.width, size.height * 0.56f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        },
        Brand.Sage.copy(alpha = 0.55f),
    )
    // A few simple trees on the far hill
    listOf(0.72f to 0.4f, 0.8f to 0.36f, 0.88f to 0.34f).forEach { (x, y) ->
        val c = Offset(size.width * x, size.height * y)
        val s = size.height * 0.07f
        drawPath(
            Path().apply {
                moveTo(c.x, c.y - s * 1.6f)
                lineTo(c.x + s * 0.7f, c.y + s * 0.4f)
                lineTo(c.x - s * 0.7f, c.y + s * 0.4f)
                close()
            },
            Brand.SagePressed.copy(alpha = 0.7f),
        )
    }
}

private fun DrawScope.drawTrail(path: PathCurve, level: Int) {
    drawPath(path.toPath(), Brand.Cream, style = Stroke(width = size.height * 0.09f, cap = StrokeCap.Round))
    // One stepping stone per day. Reached stones glow warm.
    for (i in 1..Lantern.MAX) {
        val p = path.point(0.04f + 0.9f * i / Lantern.MAX)
        val lit = i <= level
        drawCircle(if (lit) Brand.LanternLight else Brand.Mist, radius = size.height * 0.025f, center = p)
    }
}

/** Cute round bear holding the lantern up in its right paw. [height] is the bear's full height. */
private fun DrawScope.drawBear(feet: Offset, height: Float, brightness: Float) {
    val fur = Brand.BearFur
    val muzzle = Brand.BearMuzzle
    val ink = Color(0xFF2B2A24)
    val bodyW = height * 0.5f
    val bodyH = height * 0.48f
    val bodyTop = feet.y - bodyH
    val headR = height * 0.21f
    val headC = Offset(feet.x, bodyTop - headR * 0.55f)

    // Lantern first so its glow sits behind the bear
    val lanternC = Offset(feet.x + bodyW * 0.95f, bodyTop + bodyH * 0.05f)
    drawLantern(lanternC, height * 0.42f, brightness)

    // Feet
    listOf(-0.22f, 0.22f).forEach { f ->
        drawOval(fur, topLeft = Offset(feet.x + bodyW * f - bodyW * 0.18f, feet.y - height * 0.07f), size = Size(bodyW * 0.36f, height * 0.1f))
    }
    // Body and belly
    drawOval(fur, topLeft = Offset(feet.x - bodyW / 2, bodyTop), size = Size(bodyW, bodyH))
    drawOval(muzzle.copy(alpha = 0.8f), topLeft = Offset(feet.x - bodyW * 0.28f, bodyTop + bodyH * 0.3f), size = Size(bodyW * 0.56f, bodyH * 0.55f))
    // Arm reaching to the lantern handle
    drawLine(fur, Offset(feet.x + bodyW * 0.3f, bodyTop + bodyH * 0.3f), Offset(lanternC.x, lanternC.y - height * 0.2f), strokeWidth = height * 0.09f, cap = StrokeCap.Round)
    // Ears
    listOf(-0.72f, 0.72f).forEach { f ->
        val ear = Offset(headC.x + headR * f, headC.y - headR * 0.72f)
        drawCircle(fur, radius = headR * 0.38f, center = ear)
        drawCircle(muzzle, radius = headR * 0.2f, center = ear)
    }
    // Head, muzzle, face
    drawCircle(fur, radius = headR, center = headC)
    drawOval(muzzle, topLeft = Offset(headC.x - headR * 0.45f, headC.y + headR * 0.05f), size = Size(headR * 0.9f, headR * 0.65f))
    drawOval(ink, topLeft = Offset(headC.x - headR * 0.14f, headC.y + headR * 0.12f), size = Size(headR * 0.28f, headR * 0.2f))
    listOf(-0.4f, 0.4f).forEach { f ->
        drawCircle(ink, radius = headR * 0.09f, center = Offset(headC.x + headR * f, headC.y - headR * 0.12f))
    }
    // Small smile
    drawArc(
        ink, startAngle = 20f, sweepAngle = 140f, useCenter = false,
        topLeft = Offset(headC.x - headR * 0.16f, headC.y + headR * 0.22f), size = Size(headR * 0.32f, headR * 0.22f),
        style = Stroke(width = headR * 0.06f, cap = StrokeCap.Round),
    )
}
