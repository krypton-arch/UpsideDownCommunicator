// app/src/main/java/com/example/upsidedown/data/MorseCodeEncoder.kt
package com.example.upsidedown.data

/**
 * Represents a unit of Morse code signal
 */
sealed class SignalUnit(val durationMs: Long) {
    data object Dot : SignalUnit(200L)       // Short flash
    data object Dash : SignalUnit(600L)      // Long flash
    data object SymbolGap : SignalUnit(200L) // Gap between dots/dashes
    data object LetterGap : SignalUnit(600L) // Gap between letters
    data object WordGap : SignalUnit(1400L)  // Gap between words
}

/**
 * Encodes text messages into Morse code signal sequences
 */
object MorseCodeEncoder {
    
    private val morseMap = mapOf(
        'A' to ".-",    'B' to "-...",  'C' to "-.-.",  'D' to "-..",
        'E' to ".",     'F' to "..-.",  'G' to "--.",   'H' to "....",
        'I' to "..",    'J' to ".---",  'K' to "-.-",   'L' to ".-..",
        'M' to "--",    'N' to "-.",    'O' to "---",   'P' to ".--.",
        'Q' to "--.-",  'R' to ".-.",   'S' to "...",   'T' to "-",
        'U' to "..-",   'V' to "...-",  'W' to ".--",   'X' to "-..-",
        'Y' to "-.--",  'Z' to "--..",
        '0' to "-----", '1' to ".----", '2' to "..---", '3' to "...--",
        '4' to "....-", '5' to ".....", '6' to "-....", '7' to "--...",
        '8' to "---..", '9' to "----."
    )
    
    /**
     * Encodes a text string into a list of SignalUnits
     * @param text The message to encode
     * @return List of SignalUnits representing the Morse code sequence
     */
    fun encode(text: String): List<SignalUnit> {
        val signals = mutableListOf<SignalUnit>()
        val cleanText = text.uppercase().filter { it.isLetterOrDigit() || it.isWhitespace() }
        
        cleanText.forEachIndexed { index, char ->
            when {
                char.isWhitespace() -> {
                    // Remove trailing symbol gap and add word gap
                    if (signals.isNotEmpty() && signals.last() is SignalUnit.LetterGap) {
                        signals.removeLast()
                    }
                    signals.add(SignalUnit.WordGap)
                }
                char in morseMap -> {
                    val morse = morseMap[char]!!
                    morse.forEachIndexed { symbolIndex, symbol ->
                        when (symbol) {
                            '.' -> signals.add(SignalUnit.Dot)
                            '-' -> signals.add(SignalUnit.Dash)
                        }
                        // Add gap between symbols (but not after last symbol)
                        if (symbolIndex < morse.length - 1) {
                            signals.add(SignalUnit.SymbolGap)
                        }
                    }
                    // Add letter gap after each letter (will be removed if word gap follows)
                    if (index < cleanText.length - 1 && !cleanText[index + 1].isWhitespace()) {
                        signals.add(SignalUnit.LetterGap)
                    }
                }
            }
        }
        
        // Remove trailing gaps
        while (signals.isNotEmpty() && 
               (signals.last() is SignalUnit.LetterGap || 
                signals.last() is SignalUnit.SymbolGap ||
                signals.last() is SignalUnit.WordGap)) {
            signals.removeLast()
        }
        
        return signals
    }
    
    /**
     * Calculate total transmission duration in milliseconds
     */
    fun calculateDuration(signals: List<SignalUnit>): Long {
        return signals.sumOf { it.durationMs }
    }
}
