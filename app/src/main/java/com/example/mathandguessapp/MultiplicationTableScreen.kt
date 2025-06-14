package com.example.mathandguessapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MultiplicationTableScreen() {
    var number by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tabla de Multiplicar",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Ingresa un número entero") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val num = number.toIntOrNull()
                if (num != null) {
                    result = buildString {
                        for (i in 1..12) {
                            append("$num x $i = ${num * i}\n")
                        }
                    }
                } else {
                    result = "Por favor, ingresa un número entero válido."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generar Tabla")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = result,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}