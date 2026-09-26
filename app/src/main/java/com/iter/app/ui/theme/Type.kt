package com.iter.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.iter.app.R

/** Fraunces (serif): headings and page titles only. */
@OptIn(ExperimentalTextApi::class)
val Fraunces = FontFamily(
    listOf(400, 500, 600).map { w ->
        Font(R.font.fraunces, FontWeight(w), variationSettings = FontVariation.Settings(FontVariation.weight(w)))
    },
)

/** Sora (sans): everything else, including every button. */
@OptIn(ExperimentalTextApi::class)
val Sora = FontFamily(
    listOf(400, 500, 600, 700).map { w ->
        Font(R.font.sora, FontWeight(w), variationSettings = FontVariation.Settings(FontVariation.weight(w)))
    },
)

private val base = Typography()

private fun TextStyle.heading(weight: Int) = copy(fontFamily = Fraunces, fontWeight = FontWeight(weight))
private fun TextStyle.body() = copy(fontFamily = Sora)

val Typography = Typography(
    displayLarge = base.displayLarge.heading(500),
    displayMedium = base.displayMedium.heading(500),
    displaySmall = base.displaySmall.heading(500),
    headlineLarge = base.headlineLarge.heading(500),
    headlineMedium = base.headlineMedium.heading(500),
    headlineSmall = base.headlineSmall.heading(500),
    titleLarge = base.titleLarge.heading(600),
    titleMedium = base.titleMedium.body().copy(fontWeight = FontWeight.SemiBold),
    titleSmall = base.titleSmall.body().copy(fontWeight = FontWeight.SemiBold),
    bodyLarge = base.bodyLarge.body(),
    bodyMedium = base.bodyMedium.body(),
    bodySmall = base.bodySmall.body(),
    labelLarge = base.labelLarge.body().copy(fontWeight = FontWeight.SemiBold),
    labelMedium = base.labelMedium.body(),
    labelSmall = base.labelSmall.body(),
)

/** Button type scale from the spec. Same for every button variant. */
object ButtonText {
    val Large = TextStyle(fontFamily = Sora, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, letterSpacing = 0.005.em)
    val Medium = TextStyle(fontFamily = Sora, fontWeight = FontWeight.SemiBold, fontSize = 14.7.sp, letterSpacing = 0.005.em)
    val Small = TextStyle(fontFamily = Sora, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, letterSpacing = 0.01.em)
}
