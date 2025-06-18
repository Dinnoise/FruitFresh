package com.example.fruitfresh.store.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4CAF50),
    secondary = Color(0xFFE8F5E8),
    tertiary = Color(0xFF2E7D32),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color(0xFF2E7D32),
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    primaryContainer = Color(0xFF2E7D32),
    onPrimaryContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50),
    secondary = Color(0xFFE8F5E8),
    tertiary = Color(0xFF2E7D32),
    background = Color(0xFFF8FDF8),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color(0xFF2E7D32),
    onTertiary = Color.White,
    onBackground = Color(0xFF1B5E20),
    onSurface = Color(0xFF1B5E20),
    primaryContainer = Color(0xFFE8F5E8),
    onPrimaryContainer = Color(0xFF2E7D32)
)

@Composable
fun FruitFreshTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}