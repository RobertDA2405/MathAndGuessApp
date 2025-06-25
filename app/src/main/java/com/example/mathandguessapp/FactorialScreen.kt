package com.example.mathandguessapp

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.geometry.Offset

@Composable
fun FactorialScreen() {
    var number by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val isVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible.value = true }

    // Fondo con gradiente animado
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradientOffset"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0D47A1),
                        Color(0xFF1976D2).copy(alpha = 0.8f + 0.2f * gradientOffset),
                        Color(0xFF7B1FA2)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f * gradientOffset, 1000f)
                )
            )
    ) {
        // Partículas flotantes
        FloatingParticles()

        // Contenedor principal
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.75f)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0x33FFFFFF))
                .border(2.dp, Color(0xFF42A5F5), RoundedCornerShape(24.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = fadeIn(tween(1000)) + scaleIn(tween(1000))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Calcular Factorial",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    offset = Offset(4f, 4f),
                                    blurRadius = 4f
                                )
                            ),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = fadeIn(tween(1000, delayMillis = 200)) + scaleIn(tween(1000, delayMillis = 200))
                ) {
                    OutlinedTextField(
                        value = number,
                        onValueChange = { number = it },
                        label = { Text("Número entero positivo", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .border(2.dp, Color(0xFF42A5F5), RoundedCornerShape(12.dp)),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = Color.White
                        )
                    )
                }

                var isPressed by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (isPressed) 0.95f else 1f,
                    animationSpec = spring(stiffness = Spring.StiffnessMedium),
                    label = "buttonScale"
                )

                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = fadeIn(tween(1000, delayMillis = 400)) + scaleIn(tween(1000, delayMillis = 400))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF0288D1), Color(0xFF42A5F5))
                                )
                            )
                            .border(2.dp, Color(0xFF80D8FF), RoundedCornerShape(16.dp))
                            .clickable {
                                isPressed = true
                                val num = number.toIntOrNull()
                                result = when {
                                    num == null -> "Ingresa un número válido."
                                    num < 0 -> "El número debe ser positivo."
                                    num > 20 -> "Número demasiado grande."
                                    else -> try { "Factorial de $num = ${calculateFactorial(num)}" } catch (e: Exception) { "Error: ${e.message}" }
                                }
                            }
                            .scale(scale)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Calcular",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isVisible.value && result.isNotEmpty(),
                    enter = fadeIn(tween(1000, delayMillis = 600)) + scaleIn(tween(1000, delayMillis = 600))
                ) {
                    Text(
                        text = result,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.3f),
                                offset = Offset(2f, 2f),
                                blurRadius = 2f
                            )
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}

private fun calculateFactorial(n: Int): Long {
    if (n == 0 || n == 1) return 1L
    return n.toLong() * calculateFactorial(n - 1)
}