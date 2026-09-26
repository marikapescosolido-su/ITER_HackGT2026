package com.iter.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Brand colors. Fixed in light and dark mode (spec: only page chrome adapts).
 * The "Sage" names are kept, but the values are now the brighter Fern green (was #757F64).
 */
object Brand {
    val Sage = Color(0xFF62A052)
    val SageHover = Color(0xFF5A964A)
    val SagePressed = Color(0xFF528C43)
    /** Dark fern for green text on light pages (6:1 on the page); Fern itself is too light for text. */
    val SageText = Color(0xFF3F6B34)
    /** Text on Fern fills. Cream is unreadable on the brighter green (2.4:1), dark ink is 5.9:1. */
    val OnSage = Color(0xFF10130E)
    val Cream = Color(0xFFE9E2D8)
    val Mist = Color(0xFFCFE3C4)
    val MistHover = Color(0xFFBBD6AD)

    /** RESERVED for helpline / crisis messaging only. Never use for routine emphasis. */
    val Terracotta = Color(0xFFCB7A5C)
    val TerracottaHover = Color(0xFFB8663F)

    /** Lantern light (illustration only; not a UI color). Warm so it reads as hope, not alarm. */
    val LanternLight = Color(0xFFF3CE74)
    val LanternGlow = Color(0xFFF7E2A6)
    val LanternMetal = Color(0xFF3F4633)

    /** Bear mascot fur (illustration only). */
    val BearFur = Color(0xFF8B6B4E)
    val BearMuzzle = Color(0xFFD9C3A5)

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
     * Text color for outlined/text-only brand buttons: dark fern in light mode; in dark mode that is
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
    brandText = Brand.SageText,
)

val DarkChrome = Chrome(
    page = Color(0xFF16180F),
    panel = Color(0xFF1D2016),
    ink = Color(0xFFECE8DE),
    charcoal = Color(0xFFB9B6AC),
    hairline = Color(0xFFE9E2D8).copy(alpha = 0.14f),
    brandText = Brand.Mist,
)
