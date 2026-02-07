// app/src/main/java/com/example/upsidedown/ui/theme/Theme.kt
package com.example.upsidedown.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Retro dark-only color scheme - 1983 terminal aesthetic
private val RetroColorScheme = darkColorScheme(
    // Primary colors - phosphor green
    primary = PhosphorGreen,
    onPrimary = TerminalBlack,
    primaryContainer = PhosphorGreenDark,
    onPrimaryContainer = PhosphorGreen,
    
    // Secondary colors - amber
    secondary = Amber,
    onSecondary = TerminalBlack,
    secondaryContainer = AmberDim,
    onSecondaryContainer = Amber,
    
    // Tertiary colors - corruption red
    tertiary = CorruptionRed,
    onTertiary = TerminalBlack,
    tertiaryContainer = CorruptionRedDim,
    onTertiaryContainer = CorruptionRed,
    
    // Error colors
    error = CorruptionRed,
    onError = TerminalBlack,
    errorContainer = CorruptionRedDim,
    onErrorContainer = CorruptionRed,
    
    // Background and surface - pure black/near-black
    background = TerminalBlack,
    onBackground = PhosphorGreen,
    surface = TerminalBlack,
    onSurface = PhosphorGreen,
    surfaceVariant = GhostWhite,
    onSurfaceVariant = PhosphorGreenDim,
    
    // Outline
    outline = PhosphorGreenDim,
    outlineVariant = DimGray,
    
    // Inverse colors
    inverseSurface = PhosphorGreen,
    inverseOnSurface = TerminalBlack,
    inversePrimary = PhosphorGreenDark,
    
    // Scrim
    scrim = ScanlineBlack
)

@Composable
fun UpsidedownTheme(
    content: @Composable () -> Unit
) {
    // Always use retro dark theme - no dynamic colors or light theme
    MaterialTheme(
        colorScheme = RetroColorScheme,
        typography = Typography,
        content = content
    )
}