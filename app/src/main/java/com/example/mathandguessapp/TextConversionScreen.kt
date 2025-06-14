package com.example.mathandguessapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextConversionScreen() {
    var inputText by remember { mutableStateOf("") }
    var upperCaseText by remember { mutableStateOf("") }
    var lowerCaseText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Conversión de Texto",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Ingresa una frase") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                upperCaseText = inputText.uppercase()
                lowerCaseText = inputText.lowercase()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convertir")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Mayúsculas: $upperCaseText",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Minúsculas: $lowerCaseText",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}