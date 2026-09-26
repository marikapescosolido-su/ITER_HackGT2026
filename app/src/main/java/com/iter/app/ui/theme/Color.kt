package com.iter.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** Brand colors. Fixed in light and dark mode (spec: only page chrome adapts). */
object Brand {
    val Sage = Color(0xFF757F64)
    val SageHover = Color(0xFF67704F)
    val SagePressed = Color(0xFF5A6144) // also "Sage-700" text on outlined/text buttons
    val Cream = Color(0xFFE9E2D8)
    val Mist = Color(0xFFC7CDBF)
    val MistHover = Color(0xFFB1B9A9)

    /** RESERVED for helpline / crisis messaging only. Never use for routine emphasis. */
    val Terracotta = Color(0xFFCB7A5C)
    val TerracottaHover = Color(0xFFB8663F)

    /** Chart series (not in the button spec; muted so they sit next to sage). */
    val ChartSlate = Color(0xFF5E7389)
    val ChartSand = Color(0xFFB08F5A)
}

/** Page chrome. Changes between light and dark mode. */
@Immutable
data class Chrome(
    val page: Color,
    val panel: Color,
    val ink: Color,
    val charcoal: Color,
    val hairline: Color,
    /**
     * Text color for outlined/text-only brand buttons. Spec says Sage-700 (#5A6144); in dark mode that is
     * unreadable on #16180F, so dark mode uses Mist instead. Deliberate deviation for accessibility.
     */
    val brandText: Color,
)

val LightChrome = Chrome(
    page = Color(0xFFFCFAFA),
    panel = Color(0xFFF6F3EE),
    ink = Color(0xFF10130E),
    charcoal = Color(0xFF2E2E2E),
    hairline = Color(0xFF2E2E2E).copy(alpha = 0.14f),
    brandText = Brand.SagePressed,
)

val DarkChrome = Chrome(
    page = Color(0xFF16180F),
    panel = Color(0xFF1D2016),
    ink = Color(0xFFECE8DE),
    charcoal = Color(0xFFB9B6AC),
    hairline = Color(0xFFE9E2D8).copy(alpha = 0.14f),
    brandText = Brand.Mist,
)
