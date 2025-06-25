package com.example.mathandguessapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0288D1), // Azul neón
    secondary = Color(0xFF7B1FA2), // Morado
    background = Color(0xFF0D47A1), // Azul oscuro
    surface = Color(0x33FFFFFF), // Blanco translúcido
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun MathAndGuessAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}