package com.example.mathandguessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mathandguessapp.ui.theme.MathAndGuessAppTheme
import androidx.compose.ui.geometry.Offset

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathAndGuessAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun MainMenu(navController: androidx.navigation.NavController) {
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

        // Contenedor principal con glassmorphism
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.85f)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0x33FFFFFF)) // Vidrio esmerilado
                .border(2.dp, Color(0xFF42A5F5), RoundedCornerShape(24.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize()
            ) {
                // Título
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = fadeIn(tween(1000)) + scaleIn(tween(1000))
                ) {
                    Text(
                        text = "Math & Guess",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }

                // Botones
                val buttons = listOf(
                    Pair(Pair("Tabla de Multiplicar", "multiplication_table"), Icons.Default.Calculate),
                    Pair(Pair("Calcular Factorial", "factorial"), Icons.Default.Star),
                    Pair(Pair("Juego de Adivinanza", "guessing_game"), Icons.Default.Casino),
                    Pair(Pair("Conversión de Texto", "text_conversion"), Icons.Default.TextFields)
                )

                buttons.forEachIndexed { index, (pair, icon) ->
                    val (text, route) = pair
                    var isPressed by remember { mutableStateOf(false) }
                    val scale by animateFloatAsState(
                        targetValue = if (isPressed) 0.95f else 1f,
                        animationSpec = spring(stiffness = Spring.StiffnessMedium),
                        label = "buttonScale$index"
                    )

                    AnimatedVisibility(
                        visible = isVisible.value,
                        enter = fadeIn(tween(1000, delayMillis = 200 * index)) +
                                scaleIn(tween(1000, delayMillis = 200 * index))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .padding(vertical = 8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF0288D1),
                                            Color(0xFF42A5F5)
                                        )
                                    )
                                )
                                .border(2.dp, Color(0xFF80D8FF), RoundedCornerShape(16.dp))
                                .clickable {
                                    navController.navigate(route)
                                    isPressed = true
                                }
                                .scale(scale)
                                .padding(16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainMenu(navController) }
        composable("multiplication_table") { MultiplicationTableScreen() }
        composable("factorial") { FactorialScreen() }
        composable("guessing_game") { GuessingGameScreen() }
        composable("text_conversion") { TextConversionScreen() }
    }
}