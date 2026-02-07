// app/src/main/java/com/example/upsidedown/ui/screens/MainScreen.kt
package com.example.upsidedown.ui.screens

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.upsidedown.data.CommunicatorViewModel
import com.example.upsidedown.ui.components.CRTOverlay
import com.example.upsidedown.ui.components.SanityMeter
import com.example.upsidedown.ui.components.SignalDisplay
import com.example.upsidedown.ui.theme.Amber
import com.example.upsidedown.ui.theme.CorruptionRed
import com.example.upsidedown.ui.theme.PhosphorGreen
import com.example.upsidedown.ui.theme.PhosphorGreenDim
import com.example.upsidedown.ui.theme.TerminalBlack

/**
 * Main screen for the Upside Down Communicator
 * Flips upside down when possessed, displays retro terminal aesthetic
 */
@Composable
fun MainScreen(
    viewModel: CommunicatorViewModel
) {
    val sanityLevel by viewModel.sanityLevel.collectAsState()
    val isPossessed by viewModel.isPossessed.collectAsState()
    val currentMessage by viewModel.currentMessage.collectAsState()
    val isTransmitting by viewModel.isTransmitting.collectAsState()
    val isFlashing by viewModel.isFlashing.collectAsState()
    val activeLetter by viewModel.activeLetter.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    
    val focusManager = LocalFocusManager.current
    
    // Glitch animation for possessed mode
    val infiniteTransition = rememberInfiniteTransition(label = "possessed_glitch")
    val glitchOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isPossessed) 5f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glitch_offset"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TerminalBlack)
    ) {
        // Main content layer - rotates when possessed
        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    // Flip upside down when possessed
                    rotationZ = if (isPossessed) 180f else 0f
                    // Add glitch offset
                    translationX = if (isPossessed) glitchOffset else 0f
                }
                .padding(top = 48.dp, bottom = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = "╔══════════════════════════╗",
                style = MaterialTheme.typography.bodySmall,
                color = if (isPossessed) CorruptionRed else PhosphorGreenDim
            )
            Text(
                text = if (isPossessed) "SYSTEM CORRUPTED" else "UPSIDE DOWN",
                style = MaterialTheme.typography.displaySmall,
                color = if (isPossessed) CorruptionRed else PhosphorGreen
            )
            Text(
                text = if (isPossessed) "SHAKE TO RESTORE" else "COMMUNICATOR v1.983",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isPossessed) Amber else PhosphorGreenDim
            )
            Text(
                text = "╚══════════════════════════╝",
                style = MaterialTheme.typography.bodySmall,
                color = if (isPossessed) CorruptionRed else PhosphorGreenDim
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Sanity Meter
            SanityMeter(
                sanityLevel = sanityLevel,
                isPossessed = isPossessed
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Message Input Section
            if (!isPossessed) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "[ MESSAGE INPUT ]",
                        style = MaterialTheme.typography.labelMedium,
                        color = PhosphorGreenDim
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    OutlinedTextField(
                        value = currentMessage,
                        onValueChange = { viewModel.updateMessage(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, PhosphorGreenDim),
                        placeholder = {
                            Text(
                                text = "ENTER MESSAGE...",
                                color = PhosphorGreenDim.copy(alpha = 0.5f)
                            )
                        },
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = PhosphorGreen
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PhosphorGreen,
                            unfocusedBorderColor = PhosphorGreenDim,
                            cursorColor = PhosphorGreen,
                            focusedContainerColor = TerminalBlack,
                            unfocusedContainerColor = TerminalBlack
                        ),
                        shape = RectangleShape,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                viewModel.encodeAndTransmit()
                            }
                        ),
                        singleLine = true,
                        enabled = !isTransmitting
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Encode Button
                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.encodeAndTransmit()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(3.dp, PhosphorGreen),
                        enabled = currentMessage.isNotBlank() && !isTransmitting,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TerminalBlack,
                            contentColor = PhosphorGreen,
                            disabledContainerColor = TerminalBlack,
                            disabledContentColor = PhosphorGreenDim
                        ),
                        shape = RectangleShape
                    ) {
                        Text(
                            text = if (isTransmitting) ">>> TRANSMITTING <<<" else "[ ENCODE & TRANSMIT ]",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Signal Display - Stranger Things style Christmas lights
            SignalDisplay(
                isFlashing = isFlashing,
                isTransmitting = isTransmitting,
                isPossessed = isPossessed,
                activeLetter = activeLetter
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Status Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, if (isPossessed) CorruptionRed else PhosphorGreenDim)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "STATUS: $statusMessage",
                    style = MaterialTheme.typography.bodyMedium,
                    color = when {
                        isPossessed -> CorruptionRed
                        statusMessage.contains("TRANSMITTING") -> PhosphorGreen
                        statusMessage.contains("CRITICAL") -> CorruptionRed
                        statusMessage.contains("INTERFERENCE") -> Amber
                        else -> PhosphorGreenDim
                    },
                    textAlign = TextAlign.Center
                )
            }
            
            // Warning text at bottom
            if (!isPossessed && sanityLevel <= 50) {
                Spacer(modifier = Modifier.height(8.dp))
                val warningTransition = rememberInfiniteTransition(label = "warning_blink")
                val warningAlpha by warningTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 0.3f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(500, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "warning_alpha"
                )
                Text(
                    text = "⚠ WARNING: DIMENSIONAL INSTABILITY DETECTED ⚠",
                    style = MaterialTheme.typography.labelSmall,
                    color = Amber,
                    modifier = Modifier.alpha(warningAlpha),
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
        
        // CRT Overlay on top of everything
        CRTOverlay(
            modifier = Modifier.fillMaxSize(),
            enableFlicker = true
        )
    }
}
