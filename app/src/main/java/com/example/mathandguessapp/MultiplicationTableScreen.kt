package com.example.mathandguessapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
fun MultiplicationTableScreen() {
    var number by remember { mutableStateOf("") }
    var tableRows by remember { mutableStateOf<List<String>>(emptyList()) }

    // Fondo con símbolos matemáticos animados
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6F5)) // Gris claro, estilo pizarra
    ) {
        // Símbolos matemáticos animados
        AnimatedMathSymbols()

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                text = "Tabla de Multiplicar",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1E88E5), // Azul matemático
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo de texto
            OutlinedTextField(
                value = number,
                onValueChange = { number = it },
                label = { Text("Ingresa un número entero") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1E88E5),
                    unfocusedBorderColor = Color(0xFF90CAF9)
                )
            )

            // Botón para generar la tabla
            Button(
                onClick = {
                    val num = number.toIntOrNull()
                    tableRows = if (num != null) {
                        (1..12).map { i -> "$num × $i = ${num * i}" }
                    } else {
                        listOf("Por favor, ingresa un número entero válido.")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E88E5), // Azul matemático
                    contentColor = Color.White
                )
            ) {
                Text("Generar Tabla")
            }

            // Tabla de resultados
            if (tableRows.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                MultiplicationTable(tableRows = tableRows)
            }
        }
    }
}

@Composable
fun MultiplicationTable(tableRows: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, Color(0xFF90CAF9), RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        tableRows.forEachIndexed { index, row ->
            Text(
                text = row,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF37474F), // Gris oscuro, legible
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            if (index < tableRows.size - 1) {
                Divider(color = Color(0xFF90CAF9), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun AnimatedMathSymbols() {
    val symbols = listOf("+", "−", "×", "÷", "=")
    val fontSizes = listOf(20.sp, 24.sp, 28.sp, 32.sp) // Variación de tamaños
    val fontWeights = listOf(FontWeight.Normal, FontWeight.Bold) // Variación de grosor
    val infiniteTransition = rememberInfiniteTransition(label = "symbolAnimation")

    // Generar 8 símbolos aleatorios (menos para evitar saturación)
    repeat(8) {
        val xOffset by infiniteTransition.animateFloat(
            initialValue = Random.nextFloat() * 1000f,
            targetValue = Random.nextFloat() * 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(20000, easing = LinearEasing), // Más lento: 20 segundos
                repeatMode = RepeatMode.Reverse
            ),
            label = "xOffset$it"
        )
        val yOffset by infiniteTransition.animateFloat(
            initialValue = Random.nextFloat() * 1000f,
            targetValue = Random.nextFloat() * 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(20000, easing = LinearEasing), // Más lento: 20 segundos
                repeatMode = RepeatMode.Reverse
            ),
            label = "yOffset$it"
        )
        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.1f,
            targetValue = 0.3f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = LinearEasing), // Más lento: 4 segundos
                repeatMode = RepeatMode.Reverse
            ),
            label = "alpha$it"
        )

        Text(
            text = symbols[Random.nextInt(symbols.size)],
            style = TextStyle(
                fontSize = fontSizes[Random.nextInt(fontSizes.size)],
                fontWeight = fontWeights[Random.nextInt(fontWeights.size)],
                color = Color(0xFF90CAF9).copy(alpha = alpha) // Azul claro, semi-transparente
            ),
            modifier = Modifier
                .offset(x = xOffset.dp, y = yOffset.dp)
                .wrapContentSize()
        )
    }
}