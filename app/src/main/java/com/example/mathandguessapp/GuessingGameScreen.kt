package com.example.mathandguessapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun GuessingGameScreen() {
    // Estados para el juego
    var guess by remember { mutableStateOf("") }
    var secretNumber by remember { mutableStateOf(Random.nextInt(1, 11)) }
    var message by remember { mutableStateOf("Adivina el número (1-10)") }
    var attempts by remember { mutableStateOf(0) }
    var gameState by remember { mutableStateOf(GameState.Initial) }
    var showDialog by remember { mutableStateOf(false) }

    // Color de fondo animado según el estado del juego
    val backgroundColor by animateColorAsState(
        targetValue = when (gameState) {
            GameState.Initial -> Color.White
            GameState.Cold -> Color(0xFFBBDEFB) // Azul claro
            GameState.Warm -> Color(0xFFFFF9C4) // Amarillo claro
            GameState.Hot -> Color(0xFFFFCDD2) // Rojo claro
            GameState.Correct -> Color(0xFFC8E6C9) // Verde claro
        },
        label = "backgroundColor"
    )

    // Interfaz principal
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                text = "Juego de Adivinanza",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Ícono según el estado
            Text(
                text = when (gameState) {
                    GameState.Initial -> "🎲"
                    GameState.Cold -> "❄️"
                    GameState.Warm -> "🌡️"
                    GameState.Hot -> "🔥"
                    GameState.Correct -> "🎉"
                },
                fontSize = 64.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Mensaje de retroalimentación
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo de texto para el número
            OutlinedTextField(
                value = guess,
                onValueChange = { guess = it },
                label = { Text("Ingresa tu número") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            // Botón para adivinar
            Button(
                onClick = {
                    val userGuess = guess.toIntOrNull()
                    attempts++

                    if (userGuess == null || userGuess !in 1..10) {
                        message = "Por favor, ingresa un número válido entre 1 y 10."
                        gameState = GameState.Initial
                    } else {
                        val difference = abs(userGuess - secretNumber)
                        when {
                            userGuess == secretNumber -> {
                                message = "¡Correcto! El número era $secretNumber."
                                gameState = GameState.Correct
                                showDialog = true
                            }
                            difference <= 2 -> {
                                message = "¡Tibio! Estás cerca."
                                gameState = GameState.Warm
                            }
                            difference <= 4 -> {
                                message = "¡Frío! Intenta de nuevo."
                                gameState = GameState.Cold
                            }
                            else -> {
                                message = "¡Muy frío! Lejos del número."
                                gameState = GameState.Cold
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp)
            ) {
                Text("Adivinar")
            }
        }
    }

    // Diálogo de felicitación
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { /* No cerrar al tocar fuera */ },
            title = {
                Text(
                    text = "¡Felicidades!",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "Lo lograste en $attempts intentos.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Reiniciar el juego
                        guess = ""
                        secretNumber = Random.nextInt(1, 11)
                        message = "Adivina el número (1-10)"
                        attempts = 0
                        gameState = GameState.Initial
                        showDialog = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Jugar de nuevo")
                }
            }
        )
    }
}

// Enum para los estados del juego
enum class GameState {
    Initial, Cold, Warm, Hot, Correct
}