package com.terabyte.jwtnotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = White,
    primaryContainer = GreenLight,
    onPrimaryContainer = GreenDark,
    secondary = GreenDark,
    onSecondary = White,
    background = White,
    onBackground = Black,
    surface = OffWhite,
    onSurface = Black,
    error = RedError,
    onError = White
)

private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = Black,
    primaryContainer = GreenDark,
    onPrimaryContainer = GreenLight,
    secondary = GreenLight,
    onSecondary = Black,
    background = DarkBackground,
    onBackground = White,
    surface = DarkSurface,
    onSurface = White,
    error = RedError,
    onError = Black
)

@Composable
fun JwtNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}