package com.iter.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalChrome = staticCompositionLocalOf { LightChrome }

/** Shortcut for page-chrome colors: `IterTheme.chrome.panel`. */
object IterTheme {
    val chrome: Chrome @Composable get() = LocalChrome.current
}

// Material components (sliders, switches, text fields) read these; our own buttons use Brand/Chrome directly.
private fun scheme(c: Chrome, dark: Boolean) = (if (dark) darkColorScheme() else lightColorScheme()).copy(
    primary = Brand.Sage,
    onPrimary = Brand.Cream,
    primaryContainer = Brand.Mist,
    onPrimaryContainer = LightChrome.ink,
    secondary = Brand.ChartSlate,
    onSecondary = Brand.Cream,
    secondaryContainer = Brand.Mist,
    onSecondaryContainer = LightChrome.ink,
    tertiary = Brand.ChartSand,
    tertiaryContainer = Brand.Mist,
    background = c.page,
    onBackground = c.ink,
    surface = c.page,
    onSurface = c.ink,
    surfaceVariant = c.panel,
    onSurfaceVariant = c.charcoal,
    surfaceContainerLowest = c.page,
    surfaceContainerLow = c.panel,
    surfaceContainer = c.panel,
    surfaceContainerHigh = c.panel,
    surfaceContainerHighest = c.panel,
    outline = c.charcoal.copy(alpha = 0.35f), // unfocused text-field border: soft, close to the hairline style
    outlineVariant = c.hairline,
    error = Brand.TerracottaHover,
    errorContainer = Brand.Terracotta,
    onErrorContainer = Brand.Cream,
)

@Composable
fun IterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val chrome = if (darkTheme) DarkChrome else LightChrome
    CompositionLocalProvider(LocalChrome provides chrome) {
        MaterialTheme(colorScheme = scheme(chrome, darkTheme), typography = Typography, content = content)
    }
}
