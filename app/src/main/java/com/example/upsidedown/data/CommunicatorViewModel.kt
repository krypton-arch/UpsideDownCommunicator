// app/src/main/java/com/example/upsidedown/data/CommunicatorViewModel.kt
package com.example.upsidedown.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel managing the Upside Down Communicator state.
 * Handles sanity meter, possession mode, and signal transmission.
 */
class CommunicatorViewModel : ViewModel() {
    
    // Sound player for Morse code beeps (set from MainActivity)
    private var soundPlayer: MorseSoundPlayer? = null
    
    // Sanity level: 100 = fully sane, 0 = possessed
    private val _sanityLevel = MutableStateFlow(100)
    val sanityLevel: StateFlow<Int> = _sanityLevel.asStateFlow()
    
    // Is the system currently possessed (sanity = 0)
    private val _isPossessed = MutableStateFlow(false)
    val isPossessed: StateFlow<Boolean> = _isPossessed.asStateFlow()
    
    // Current message input by user
    private val _currentMessage = MutableStateFlow("")
    val currentMessage: StateFlow<String> = _currentMessage.asStateFlow()
    
    // Encoded signal output
    private val _signalOutput = MutableStateFlow<List<SignalUnit>>(emptyList())
    val signalOutput: StateFlow<List<SignalUnit>> = _signalOutput.asStateFlow()
    
    // Is currently transmitting a signal
    private val _isTransmitting = MutableStateFlow(false)
    val isTransmitting: StateFlow<Boolean> = _isTransmitting.asStateFlow()
    
    // Current signal being displayed (true = flash on, false = flash off)
    private val _isFlashing = MutableStateFlow(false)
    val isFlashing: StateFlow<Boolean> = _isFlashing.asStateFlow()
    
    // Currently active letter (for Stranger Things style display)
    private val _activeLetter = MutableStateFlow<Char?>(null)
    val activeLetter: StateFlow<Char?> = _activeLetter.asStateFlow()
    
    // Status message for display
    private val _statusMessage = MutableStateFlow("SIGNAL READY")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()
    
    private var sanityDrainJob: Job? = null
    private var transmissionJob: Job? = null
    private var possessionRecoveryJob: Job? = null
    
    // Configuration
    private val sanityDrainIntervalMs = 1000L // Drain 1 point per second
    private val possessionDurationMs = 30_000L // 30 seconds of possession before auto-recovery
    
    /**
     * Set the sound player (called from MainActivity)
     */
    fun setSoundPlayer(player: MorseSoundPlayer) {
        soundPlayer = player
    }
    
    init {
        startSanityDrain()
    }
    
    /**
     * Start the sanity drain timer
     */
    private fun startSanityDrain() {
        sanityDrainJob?.cancel()
        sanityDrainJob = viewModelScope.launch {
            while (true) {
                delay(sanityDrainIntervalMs)
                if (!_isPossessed.value && _sanityLevel.value > 0) {
                    _sanityLevel.value -= 1
                    
                    // Update status based on sanity level
                    when {
                        _sanityLevel.value <= 0 -> enterPossessedMode()
                        _sanityLevel.value <= 20 -> _statusMessage.value = "SYSTEM CRITICAL"
                        _sanityLevel.value <= 50 -> _statusMessage.value = "INTERFERENCE DETECTED"
                    }
                }
            }
        }
    }
    
    /**
     * Enter possessed/corrupted mode
     */
    private fun enterPossessedMode() {
        _isPossessed.value = true
        _statusMessage.value = "SYSTEM CORRUPTED"
        _isTransmitting.value = false
        transmissionJob?.cancel()
        
        // Start auto-recovery timer
        possessionRecoveryJob?.cancel()
        possessionRecoveryJob = viewModelScope.launch {
            delay(possessionDurationMs)
            restoreSanity()
        }
    }
    
    /**
     * Restore sanity (called on shake or auto-recovery)
     */
    fun restoreSanity() {
        possessionRecoveryJob?.cancel()
        _isPossessed.value = false
        _sanityLevel.value = 100
        _statusMessage.value = "SIGNAL READY"
    }
    
    /**
     * Update the current message
     */
    fun updateMessage(message: String) {
        _currentMessage.value = message
    }
    
    /**
     * Encode the current message and start transmission
     * Uses Stranger Things style - lights up individual letters
     */
    fun encodeAndTransmit() {
        if (_isPossessed.value) return
        if (_currentMessage.value.isBlank()) return
        if (_isTransmitting.value) return
        
        // Start letter-by-letter transmission (Stranger Things style)
        startLetterTransmission(_currentMessage.value.uppercase())
    }
    
    /**
     * Start transmitting letters one by one (like Christmas lights)
     */
    private fun startLetterTransmission(message: String) {
        transmissionJob?.cancel()
        transmissionJob = viewModelScope.launch {
            _isTransmitting.value = true
            _statusMessage.value = "TRANSMITTING..."
            
            // Filter to only valid characters
            val validChars = message.filter { it.isLetter() || it.isDigit() || it.isWhitespace() }
            
            for (char in validChars) {
                if (_isPossessed.value) {
                    break
                }
                
                if (char.isWhitespace()) {
                    // Word gap - brief pause, no light
                    _activeLetter.value = null
                    _isFlashing.value = false
                    delay(800L)
                } else {
                    // Light up this letter and play sound
                    _activeLetter.value = char.uppercaseChar()
                    _isFlashing.value = true
                    soundPlayer?.playLetterBeep()
                    
                    // Hold the letter lit for visibility
                    delay(600L)
                    
                    // Brief flicker off
                    _isFlashing.value = false
                    _activeLetter.value = null
                    delay(300L)
                }
            }
            
            _isTransmitting.value = false
            _isFlashing.value = false
            _activeLetter.value = null
            
            if (!_isPossessed.value) {
                _statusMessage.value = "TRANSMISSION COMPLETE"
                delay(2000)
                if (!_isTransmitting.value && !_isPossessed.value) {
                    _statusMessage.value = "SIGNAL READY"
                }
            }
        }
    }
    
    /**
     * Stop current transmission
     */
    fun stopTransmission() {
        transmissionJob?.cancel()
        _isTransmitting.value = false
        _isFlashing.value = false
        _statusMessage.value = "SIGNAL READY"
    }
    
    override fun onCleared() {
        super.onCleared()
        sanityDrainJob?.cancel()
        transmissionJob?.cancel()
        possessionRecoveryJob?.cancel()
    }
}
