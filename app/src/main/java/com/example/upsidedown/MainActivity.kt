// app/src/main/java/com/example/upsidedown/MainActivity.kt
package com.example.upsidedown

import android.os.Bundle
import android.os.Vibrator
import android.os.VibratorManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upsidedown.data.CommunicatorViewModel
import com.example.upsidedown.data.MorseSoundPlayer
import com.example.upsidedown.data.ShakeDetector
import com.example.upsidedown.ui.screens.MainScreen
import com.example.upsidedown.ui.screens.SplashScreen
import com.example.upsidedown.ui.theme.UpsidedownTheme

/**
 * Main Activity for the Upside Down Communicator
 * Manages lifecycle for shake detection and ViewModel
 */
class MainActivity : ComponentActivity() {
    
    private var shakeDetector: ShakeDetector? = null
    private var vibrator: Vibrator? = null
    private var soundPlayer: MorseSoundPlayer? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        
        // Hide system bars for immersive experience
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
        windowInsetsController.systemBarsBehavior = 
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        
        // Initialize vibrator
        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        
        // Initialize sound player for Morse code beeps
        soundPlayer = MorseSoundPlayer(this)
        
        setContent {
            UpsidedownTheme {
                // Track whether splash is complete
                var showSplash by remember { mutableStateOf(true) }
                
                val communicatorViewModel: CommunicatorViewModel = viewModel()
                
                // Set sound player to ViewModel
                soundPlayer?.let { communicatorViewModel.setSoundPlayer(it) }
                
                // Initialize shake detector with callback
                if (shakeDetector == null) {
                    shakeDetector = ShakeDetector(this) {
                        // Vibrate on shake
                        @Suppress("DEPRECATION")
                        vibrator?.vibrate(200)
                        // Restore sanity
                        communicatorViewModel.restoreSanity()
                    }
                }
                
                if (showSplash) {
                    SplashScreen(
                        onSplashComplete = { showSplash = false }
                    )
                } else {
                    MainScreen(
                        viewModel = communicatorViewModel
                    )
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        shakeDetector?.start()
    }
    
    override fun onPause() {
        super.onPause()
        shakeDetector?.stop()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        shakeDetector?.stop()
        shakeDetector = null
    }
}