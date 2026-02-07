// app/src/main/java/com/example/upsidedown/ui/components/CRTOverlay.kt
package com.example.upsidedown.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

/**
 * CRT monitor overlay effect with horizontal scanlines
 * Creates authentic 1983 terminal aesthetic
 */
@Composable
fun CRTOverlay(
    modifier: Modifier = Modifier,
    scanlineColor: Color = Color.Black.copy(alpha = 0.15f),
    scanlineSpacing: Float = 4f,
    enableFlicker: Boolean = true
) {
    // Subtle flicker animation
    val infiniteTransition = rememberInfiniteTransition(label = "crt_flicker")
    val flickerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.12f,
        targetValue = 0.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flicker_alpha"
    )
    
    val actualAlpha = if (enableFlicker) flickerAlpha else 0.15f
    
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        
        // Draw horizontal scanlines
        var y = 0f
        while (y < height) {
            drawLine(
                color = scanlineColor.copy(alpha = actualAlpha),
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 2f
            )
            y += scanlineSpacing
        }
        
        // Add subtle vignette effect at corners
        // Using gradual darkening from corners
        val vignetteColor = Color.Black.copy(alpha = 0.3f)
        
        // Top-left corner
        drawCircle(
            color = vignetteColor,
            radius = width * 0.3f,
            center = Offset(0f, 0f)
        )
        
        // Top-right corner
        drawCircle(
            color = vignetteColor,
            radius = width * 0.3f,
            center = Offset(width, 0f)
        )
        
        // Bottom-left corner
        drawCircle(
            color = vignetteColor,
            radius = width * 0.3f,
            center = Offset(0f, height)
        )
        
        // Bottom-right corner
        drawCircle(
            color = vignetteColor,
            radius = width * 0.3f,
            center = Offset(width, height)
        )
    }
}
