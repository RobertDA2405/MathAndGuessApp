package com.example.mathandguessapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FactorialScreen() {
    var number by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calcular Factorial",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Ingresa un número entero positivo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val num = number.toIntOrNull()
                result = when {
                    num == null -> "Por favor, ingresa un número entero válido."
                    num < 0 -> "El número debe ser positivo."
                    num > 20 -> "El número es demasiado grande para calcular el factorial."
                    else -> {
                        try {
                            "Factorial de $num = ${calculateFactorial(num)}"
                        } catch (e: Exception) {
                            "Error al calcular el factorial: ${e.message}"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular Factorial")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = result,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun calculateFactorial(n: Int): Long {
    if (n == 0 || n == 1) return 1L
    return n * calculateFactorial(n - 1)
}