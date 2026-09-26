package com.team.checkin.ui.theme

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
    background = Mist,
    onBackground = Ink,
    surface = Mist,
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
    background = Night,
    onBackground = InkDark,
    surface = NightSurface,
    onSurface = InkDark,
)

@Composable
fun CheckinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content,
    )
}
