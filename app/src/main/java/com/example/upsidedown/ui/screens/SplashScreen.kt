// app/src/main/java/com/example/upsidedown/ui/screens/SplashScreen.kt
package com.example.upsidedown.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upsidedown.ui.components.CRTOverlay
import com.example.upsidedown.ui.theme.CorruptionRed
import com.example.upsidedown.ui.theme.PhosphorGreen
import com.example.upsidedown.ui.theme.PhosphorGreenDim
import com.example.upsidedown.ui.theme.TerminalBlack
import kotlinx.coroutines.delay

/**
 * Splash screen with animated logo in Stranger Things style
 */
@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    // Fade in animation
    val fadeInAlpha = remember { Animatable(0f) }
    
    // Glow pulse animation
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )
    
    // Flicker effect
    val flickerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flicker"
    )
    
    LaunchedEffect(Unit) {
        // Fade in
        fadeInAlpha.animateTo(1f, animationSpec = tween(1000))
        // Wait for display
        delay(2500)
        // Complete splash
        onSplashComplete()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A)),
        contentAlignment = Alignment.Center
    ) {
        // Glow background effect
        Box(
            modifier = Modifier
                .size(300.dp)
                .blur(60.dp)
                .alpha(glowAlpha)
                .background(CorruptionRed.copy(alpha = 0.3f))
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(fadeInAlpha.value * flickerAlpha)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Top decorative line
            Text(
                text = "━━━━━━━━━━━━━━━━━━━━",
                color = CorruptionRed,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // "THE" text
            Text(
                text = "THE",
                color = CorruptionRed,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 8.sp
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // "UPSIDE" text with glow
            Box(contentAlignment = Alignment.Center) {
                // Glow layer
                Text(
                    text = "UPSIDE",
                    color = CorruptionRed.copy(alpha = glowAlpha),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp,
                    modifier = Modifier.blur(8.dp)
                )
                // Main text
                Text(
                    text = "UPSIDE",
                    color = CorruptionRed,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp
                )
            }
            
            // "DOWN" text with glow
            Box(contentAlignment = Alignment.Center) {
                // Glow layer
                Text(
                    text = "DOWN",
                    color = CorruptionRed.copy(alpha = glowAlpha),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp,
                    modifier = Modifier.blur(8.dp)
                )
                // Main text
                Text(
                    text = "DOWN",
                    color = CorruptionRed,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Bottom decorative line
            Text(
                text = "━━━━━━━━━━━━━━━━━━━━",
                color = CorruptionRed,
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Subtitle
            Text(
                text = "COMMUNICATOR",
                color = PhosphorGreenDim,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 6.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Loading indicator
            Text(
                text = "ESTABLISHING CONNECTION...",
                color = PhosphorGreen.copy(alpha = flickerAlpha),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
        
        // CRT Overlay
        CRTOverlay(
            modifier = Modifier.fillMaxSize(),
            enableFlicker = true
        )
    }
}
