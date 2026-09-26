package com.iter.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Sage,
    onPrimary = Mist,
    primaryContainer = SageLight,
    onPrimaryContainer = SageDark,
    secondary = CalmBlue,
    onSecondary = Mist,
    secondaryContainer = CalmBlueLight,
    onSecondaryContainer = CalmBlueDark,
    tertiary = Sand,
    tertiaryContainer = SandLight,
    background = Mist,
    onBackground = Ink,
    surface = Mist,
    surfaceVariant = SageSurface2,
    surfaceContainerLow = SageSurface1,
    surfaceContainer = SageSurface2,
    surfaceContainerHigh = SageSurface3,
    onSurface = Ink,
)

private val DarkColors = darkColorScheme(
    primary = SageLight,
    onPrimary = SageDark,
    primaryContainer = SageDark,
    onPrimaryContainer = SageLight,
    secondary = CalmBlueLight,
    onSecondary = CalmBlueDark,
    secondaryContainer = CalmBlueDark,
    onSecondaryContainer = CalmBlueLight,
    tertiary = SandLight,
    tertiaryContainer = Sand,
    background = Night,
    onBackground = InkDark,
    surface = NightSurface,
    surfaceVariant = NightSurface2,
    surfaceContainerLow = NightSurface1,
    surfaceContainer = NightSurface2,
    surfaceContainerHigh = NightSurface3,
    onSurface = InkDark,
)

@Composable
fun IterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content,
    )
}
