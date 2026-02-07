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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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

// Christmas light colors like in Stranger Things
private val lightColors = listOf(
    Color(0xFFFF0000), // Red
    Color(0xFFFFFF00), // Yellow
    Color(0xFF00FF00), // Green
    Color(0xFF0088FF), // Blue
    Color(0xFFFF00FF), // Magenta
    Color(0xFFFF8800), // Orange
    Color(0xFF00FFFF), // Cyan
    Color(0xFFFF0088), // Pink
)

/**
 * Stranger Things style alphabet display
 * Letters are arranged in rows like Christmas lights on a wall
 * Individual letters light up to spell messages
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignalDisplay(
    isFlashing: Boolean,
    isTransmitting: Boolean,
    isPossessed: Boolean,
    activeLetter: Char? = null,
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
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Label
        Text(
            text = "[ DIMENSIONAL INTERFACE ]",
            style = MaterialTheme.typography.labelMedium,
            color = if (isPossessed) CorruptionRed else PhosphorGreenDim
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Alphabet wall - like Joyce's Christmas lights
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, if (isPossessed) CorruptionRed else PhosphorGreenDim)
                .background(Color(0xFF0D0D0D))
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Row 1: A-I
                AlphabetRow(
                    letters = "ABCDEFGHI",
                    activeLetter = activeLetter,
                    isPossessed = isPossessed,
                    flickerAlpha = flickerAlpha
                )
                
                // Row 2: J-R
                AlphabetRow(
                    letters = "JKLMNOPQR",
                    activeLetter = activeLetter,
                    isPossessed = isPossessed,
                    flickerAlpha = flickerAlpha
                )
                
                // Row 3: S-Z + numbers hint
                AlphabetRow(
                    letters = "STUVWXYZ",
                    activeLetter = activeLetter,
                    isPossessed = isPossessed,
                    flickerAlpha = flickerAlpha
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Status indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .border(1.dp, if (isPossessed) CorruptionRed else PhosphorGreenDim)
                .background(TerminalBlack)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            val statusText = when {
                isPossessed -> ">>> INTERFERENCE <<<"
                isTransmitting && activeLetter != null -> "RECEIVING: $activeLetter"
                isTransmitting -> "SIGNAL INCOMING..."
                else -> "AWAITING TRANSMISSION"
            }
            
            Text(
                text = statusText,
                style = MaterialTheme.typography.bodyMedium,
                color = when {
                    isPossessed -> CorruptionRed
                    isTransmitting -> PhosphorGreen
                    else -> PhosphorGreenDim
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AlphabetRow(
    letters: String,
    activeLetter: Char?,
    isPossessed: Boolean,
    flickerAlpha: Float
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        letters.forEachIndexed { index, letter ->
            ChristmasLight(
                letter = letter,
                isActive = activeLetter == letter,
                lightColor = lightColors[index % lightColors.size],
                isPossessed = isPossessed,
                flickerAlpha = flickerAlpha
            )
        }
    }
}

@Composable
private fun ChristmasLight(
    letter: Char,
    isActive: Boolean,
    lightColor: Color,
    isPossessed: Boolean,
    flickerAlpha: Float
) {
    val actualColor = when {
        isPossessed -> CorruptionRed
        isActive -> lightColor
        else -> Color.Gray.copy(alpha = 0.3f)
    }
    
    val glowColor by animateColorAsState(
        targetValue = if (isActive) lightColor else Color.Transparent,
        animationSpec = tween(100),
        label = "glow"
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 2.dp)
    ) {
        // The light bulb
        Box(
            contentAlignment = Alignment.Center
        ) {
            // Glow effect when active
            if (isActive && !isPossessed) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .blur(12.dp)
                        .clip(CircleShape)
                        .background(glowColor.copy(alpha = 0.8f * flickerAlpha))
                )
            }
            
            // The bulb itself
            Box(
                modifier = Modifier
                    .size(if (isActive) 28.dp else 24.dp)
                    .clip(CircleShape)
                    .background(
                        if (isActive) actualColor.copy(alpha = flickerAlpha)
                        else actualColor
                    )
                    .border(
                        width = 2.dp,
                        color = if (isActive) actualColor else Color.DarkGray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Inner bright spot when lit
                if (isActive) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.8f))
                    )
                }
            }
        }
        
        // Letter label below the light
        Text(
            text = letter.toString(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 12.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
            ),
            color = if (isActive) {
                if (isPossessed) CorruptionRed else PhosphorGreen
            } else {
                PhosphorGreenDim.copy(alpha = 0.6f)
            }
        )
    }
}
