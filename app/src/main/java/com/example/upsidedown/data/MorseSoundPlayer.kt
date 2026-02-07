// app/src/main/java/com/example/upsidedown/data/MorseSoundPlayer.kt
package com.example.upsidedown.data

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlin.math.sin

/**
 * Plays Morse code beep sounds using synthesized audio
 * Creates classic telegraph-style beeps
 */
class MorseSoundPlayer(context: Context) {
    
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    
    // Audio parameters
    private val sampleRate = 44100
    private val frequency = 800  // Hz - classic telegraph tone
    private val dotDurationMs = 150
    private val dashDurationMs = 450
    
    // Volume (0.0 to 1.0)
    private var volume = 0.7f
    
    /**
     * Play a dot sound (short beep)
     */
    fun playDot() {
        playTone(dotDurationMs)
    }
    
    /**
     * Play a dash sound (long beep)
     */
    fun playDash() {
        playTone(dashDurationMs)
    }
    
    /**
     * Play a beep for a letter highlight (medium beep)
     */
    fun playLetterBeep() {
        playTone(200)
    }
    
    /**
     * Play a corruption/glitch sound
     */
    fun playCorruptionSound() {
        // Play a jarring lower frequency
        playTone(100, 200)
    }
    
    /**
     * Generate and play a sine wave tone
     */
    private fun playTone(durationMs: Int, freqOverride: Int = frequency) {
        Thread {
            try {
                val numSamples = (sampleRate * durationMs) / 1000
                val samples = ShortArray(numSamples)
                
                // Generate sine wave with envelope
                for (i in 0 until numSamples) {
                    val angle = 2.0 * Math.PI * i / (sampleRate / freqOverride)
                    
                    // Apply envelope (fade in/out) to avoid clicks
                    val envelope = when {
                        i < numSamples * 0.05 -> i / (numSamples * 0.05f)  // Fade in
                        i > numSamples * 0.95 -> (numSamples - i) / (numSamples * 0.05f)  // Fade out
                        else -> 1f
                    }
                    
                    samples[i] = (sin(angle) * Short.MAX_VALUE * volume * envelope).toInt().toShort()
                }
                
                // Create and play AudioTrack
                val bufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )
                
                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setSampleRate(sampleRate)
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(maxOf(bufferSize, samples.size * 2))
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()
                
                audioTrack.write(samples, 0, samples.size)
                audioTrack.play()
                
                // Wait for playback to complete
                Thread.sleep(durationMs.toLong() + 50)
                
                audioTrack.stop()
                audioTrack.release()
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
    
    /**
     * Set the volume (0.0 to 1.0)
     */
    fun setVolume(vol: Float) {
        volume = vol.coerceIn(0f, 1f)
    }
}
