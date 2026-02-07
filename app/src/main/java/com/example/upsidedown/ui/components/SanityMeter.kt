// app/src/main/java/com/example/upsidedown/ui/components/SanityMeter.kt
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.upsidedown.ui.theme.Amber
import com.example.upsidedown.ui.theme.CorruptionRed
import com.example.upsidedown.ui.theme.PhosphorGreen
import com.example.upsidedown.ui.theme.PhosphorGreenDim
import com.example.upsidedown.ui.theme.TerminalBlack

/**
 * Retro-styled sanity meter showing system stability
 * Changes color based on sanity level - green > amber > red
 */
@Composable
fun SanityMeter(
    sanityLevel: Int,
    isPossessed: Boolean,
    modifier: Modifier = Modifier
) {
    // Determine color based on sanity level
    val meterColor by animateColorAsState(
        targetValue = when {
            isPossessed -> CorruptionRed
            sanityLevel <= 20 -> CorruptionRed
            sanityLevel <= 50 -> Amber
            else -> PhosphorGreen
        },
        animationSpec = tween(300),
        label = "meter_color"
    )
    
    // Glitch/pulse animation for low sanity
    val infiniteTransition = rememberInfiniteTransition(label = "sanity_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (sanityLevel <= 20 || isPossessed) 0.4f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (isPossessed) 100 else 500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Label row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "[ SANITY ]",
                style = MaterialTheme.typography.labelMedium,
                color = PhosphorGreenDim
            )
            Text(
                text = if (isPossessed) "CORRUPTED" else "$sanityLevel%",
                style = MaterialTheme.typography.labelMedium,
                color = meterColor,
                modifier = Modifier.alpha(pulseAlpha)
            )
        }
        
        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .border(2.dp, PhosphorGreenDim)
                .background(TerminalBlack)
                .padding(2.dp)
        ) {
            // Fill bar
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = sanityLevel / 100f)
                    .alpha(pulseAlpha)
                    .background(meterColor)
            )
            
            // Segmented overlay (retro bar segments)
            Row(
                modifier = Modifier.matchParentSize()
            ) {
                repeat(20) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(
                                width = 1.dp,
                                color = if (index < (sanityLevel / 5)) 
                                    Color.Transparent 
                                else 
                                    TerminalBlack.copy(alpha = 0.5f)
                            )
                    )
                }
            }
        }
    }
}
