package com.example.mathandguessapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun FloatingParticles() {
    val particles = List(10) {
        Particle(
            x = Random.nextFloat() * 1000f,
            y = Random.nextFloat() * 1000f,
            size = Random.nextFloat() * 5f + 2f
        )
    }
    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    particles.forEachIndexed { index, particle ->
        val offset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 20f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000 + index * 200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "particle$index"
        )
        Box(
            modifier = Modifier
                .offset(x = particle.x.dp, y = (particle.y + offset).dp)
                .size(particle.size.dp)
                .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(50))
        )
    }
}

data class Particle(val x: Float, val y: Float, val size: Float)