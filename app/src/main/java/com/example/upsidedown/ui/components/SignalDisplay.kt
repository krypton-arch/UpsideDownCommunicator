// app/src/main/java/com/example/upsidedown/ui/components/SignalDisplay.kt
package com.example.upsidedown.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upsidedown.ui.theme.Amber
import com.example.upsidedown.ui.theme.CorruptionRed
import com.example.upsidedown.ui.theme.PhosphorGreen
import com.example.upsidedown.ui.theme.PhosphorGreenDim
import com.example.upsidedown.ui.theme.TerminalBlack

/**
 * Morse code signal display
 * Shows flashing light for dots and dashes - requires deciphering
 * No plain letters shown
 */
@Composable
fun SignalDisplay(
    isFlashing: Boolean,
    isTransmitting: Boolean,
    isPossessed: Boolean,
    activeLetter: Char? = null, // Kept for compatibility but not used
    modifier: Modifier = Modifier
) {
    // Flicker animation for possessed mode
    val infiniteTransition = rememberInfiniteTransition(label = "light_flicker")
    val flickerAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isPossessed) 0.2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (isPossessed) 80 else 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flicker"
    )
    
    // Glow pulse for active signal
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_pulse"
    )
    
    val signalColor by animateColorAsState(
        targetValue = when {
            isPossessed -> CorruptionRed
            isFlashing -> PhosphorGreen
            else -> PhosphorGreenDim.copy(alpha = 0.3f)
        },
        animationSpec = tween(50),
        label = "signal_color"
    )
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Label
        Text(
            text = "[ SIGNAL TRANSMISSION ]",
            style = MaterialTheme.typography.labelMedium,
            color = if (isPossessed) CorruptionRed else PhosphorGreenDim
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Main signal display area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .border(3.dp, if (isPossessed) CorruptionRed else PhosphorGreenDim)
                .background(TerminalBlack),
            contentAlignment = Alignment.Center
        ) {
            // Glow effect behind the signal
            if (isFlashing && !isPossessed) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .blur(40.dp)
                        .background(PhosphorGreen.copy(alpha = 0.6f * glowPulse))
                )
            }
            
            // The signal light
            Box(
                modifier = Modifier
                    .size(if (isFlashing) 100.dp else 80.dp)
                    .clip(CircleShape)
                    .background(signalColor.copy(alpha = flickerAlpha))
                    .border(
                        width = 4.dp,
                        color = if (isFlashing) signalColor else PhosphorGreenDim,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Inner bright core when lit
                if (isFlashing) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.8f * flickerAlpha))
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Legend - Morse code reference
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, PhosphorGreenDim)
                .background(TerminalBlack)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Dot indicator
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(PhosphorGreen)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "SHORT = DOT (.)",
                        style = MaterialTheme.typography.labelSmall,
                        color = PhosphorGreenDim
                    )
                }
                
                // Dash indicator
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(PhosphorGreen)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "LONG = DASH (-)",
                        style = MaterialTheme.typography.labelSmall,
                        color = PhosphorGreenDim
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Status
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, if (isPossessed) CorruptionRed else PhosphorGreenDim)
                .background(TerminalBlack)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            val statusText = when {
                isPossessed -> ">>> SIGNAL CORRUPTED <<<"
                isTransmitting && isFlashing -> "■ SIGNAL ACTIVE ■"
                isTransmitting -> "TRANSMITTING..."
                else -> "AWAITING SIGNAL"
            }
            
            Text(
                text = statusText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isFlashing) FontWeight.Bold else FontWeight.Normal
                ),
                color = when {
                    isPossessed -> CorruptionRed
                    isFlashing -> PhosphorGreen
                    isTransmitting -> Amber
                    else -> PhosphorGreenDim
                },
                textAlign = TextAlign.Center
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Warning label
        Text(
            text = "⚠ DECIPHER MORSE CODE TO READ MESSAGE ⚠",
            style = MaterialTheme.typography.labelSmall,
            color = Amber.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}
