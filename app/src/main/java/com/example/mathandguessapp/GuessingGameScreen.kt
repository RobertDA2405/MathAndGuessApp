package com.example.mathandguessapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun GuessingGameScreen() {
    var guess by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("Adivina el número (1-10)") }
    var secretNumber by remember { mutableStateOf(Random.nextInt(1, 11)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Juego de Adivinanza",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = guess,
            onValueChange = { guess = it },
            label = { Text("Ingresa tu número") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val userGuess = guess.toIntOrNull()
                if (userGuess != null && userGuess in 1..10) {
                    message = when {
                        userGuess == secretNumber -> {
                            "¡Correcto! El número era $secretNumber. ¡Juega de nuevo!"
                            secretNumber = Random.nextInt(1, 11)
                            guess = ""
                            message
                        }
                        userGuess < secretNumber -> "El número es mayor. Intenta de nuevo."
                        else -> "El número es menor. Intenta de nuevo."
                    }
                } else {
                    message = "Por favor, ingresa un número válido entre 1 y 10."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adivinar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}