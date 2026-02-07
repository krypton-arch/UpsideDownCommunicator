// app/src/main/java/com/example/upsidedown/ui/components/SignalDisplay.kt
package com.example.upsidedown.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.upsidedown.ui.theme.CorruptionRed
import com.example.upsidedown.ui.theme.PhosphorGreen
import com.example.upsidedown.ui.theme.PhosphorGreenDim
import com.example.upsidedown.ui.theme.TerminalBlack

/**
 * Visual display for Morse code signal output
 * Shows a large flashing area that pulses with dots and dashes
 * Does NOT display the original text - judges must decode visually
 */
@Composable
fun SignalDisplay(
    isFlashing: Boolean,
    isTransmitting: Boolean,
    isPossessed: Boolean,
    modifier: Modifier = Modifier
) {
    // Animate the flash color
    val flashColor by animateColorAsState(
        targetValue = when {
            isPossessed -> if (isFlashing) CorruptionRed else TerminalBlack
            isFlashing -> PhosphorGreen
            else -> TerminalBlack
        },
        animationSpec = tween(50),
        label = "flash_color"
    )
    
    val borderColor = when {
        isPossessed -> CorruptionRed
        isTransmitting -> PhosphorGreen
        else -> PhosphorGreenDim
    }
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Label
        Text(
            text = "[ SIGNAL OUTPUT ]",
            style = MaterialTheme.typography.labelMedium,
            color = PhosphorGreenDim
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Main flash display area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(3.dp, borderColor)
                .background(TerminalBlack),
            contentAlignment = Alignment.Center
        ) {
            // Glow effect layer (behind)
            if (isFlashing) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .blur(40.dp)
                        .background(
                            if (isPossessed) CorruptionRed.copy(alpha = 0.5f)
                            else PhosphorGreen.copy(alpha = 0.5f)
                        )
                )
            }
            
            // Main flash indicator
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .border(
                        width = 4.dp,
                        color = borderColor
                    )
                    .background(flashColor),
                contentAlignment = Alignment.Center
            ) {
                if (!isTransmitting) {
                    Text(
                        text = if (isPossessed) "×××" else ">>>",
                        style = MaterialTheme.typography.headlineLarge,
                        color = if (isPossessed) CorruptionRed else PhosphorGreenDim
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Signal legend
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SignalLegendItem(
                label = "DOT",
                description = "SHORT",
                color = PhosphorGreenDim
            )
            SignalLegendItem(
                label = "DASH",
                description = "LONG",
                color = PhosphorGreenDim
            )
        }
    }
}

@Composable
private fun SignalLegendItem(
    label: String,
    description: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = color.copy(alpha = 0.6f)
        )
    }
}
