// app/src/main/java/com/example/upsidedown/ui/theme/Color.kt
package com.example.upsidedown.ui.theme

import androidx.compose.ui.graphics.Color

// Retro terminal colors - 1983 aesthetic
val PhosphorGreen = Color(0xFF33FF00)      // Primary CRT phosphor green
val PhosphorGreenDim = Color(0xFF1A7F00)   // Dimmed green for secondary elements
val PhosphorGreenDark = Color(0xFF0D3F00)  // Very dark green for backgrounds

val Amber = Color(0xFFFFB000)               // Warning/caution amber
val AmberDim = Color(0xFF7F5800)            // Dimmed amber

val TerminalBlack = Color(0xFF0A0A0A)       // Near-black background
val ScanlineBlack = Color(0xFF000000)       // Pure black for scanlines

val CorruptionRed = Color(0xFFFF0000)       // Danger/corruption indicator
val CorruptionRedDim = Color(0xFF7F0000)    // Dimmed corruption

val GhostWhite = Color(0xFF1A1A1A)          // Subtle highlight
val DimGray = Color(0xFF333333)             // Border color

// Legacy colors (kept for compatibility, but we'll override in theme)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)