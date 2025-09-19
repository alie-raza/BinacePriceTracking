package com.alidev.binancepricetracking.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Dark color scheme
private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    secondary = Orange,
    background = Dark100,
    surface = Dark600,
    error = Purple200,
    onPrimary = Light100,
    onSecondary = Light100,
    onBackground = Color(0xFFF8F8F7),
    onSurface = Color(0xFFEAECEF),
    onError = Purple200
)

// Light color scheme
private val LightColorScheme = lightColorScheme(
    primary = Purple500,
    secondary = Teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = Purple200,
    onError = Color.White
)

@Composable
fun BinancePriceTrackingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // <-- should be Material3 Typography
        shapes = Shapes,
        content = content
    )
}
