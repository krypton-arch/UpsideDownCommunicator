// app/src/main/java/com/example/upsidedown/data/ShakeDetector.kt
package com.example.upsidedown.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

/**
 * Detects device shake gestures using the accelerometer.
 * Used to restore sanity when the system is "possessed".
 */
class ShakeDetector(
    context: Context,
    private val onShake: () -> Unit
) : SensorEventListener {
    
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    
    // Shake detection parameters
    private val shakeThreshold = 12f  // m/s² threshold for shake detection
    private val shakeCooldownMs = 500L // Minimum time between shake events
    
    private var lastShakeTime = 0L
    private var isRegistered = false
    
    /**
     * Start listening for shake events
     */
    fun start() {
        if (!isRegistered && accelerometer != null) {
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_UI
            )
            isRegistered = true
        }
    }
    
    /**
     * Stop listening for shake events
     */
    fun stop() {
        if (isRegistered) {
            sensorManager.unregisterListener(this)
            isRegistered = false
        }
    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val x = it.values[0]
                val y = it.values[1]
                val z = it.values[2]
                
                // Calculate acceleration magnitude (subtract gravity approximation)
                val acceleration = sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH
                
                if (acceleration > shakeThreshold) {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastShakeTime > shakeCooldownMs) {
                        lastShakeTime = currentTime
                        onShake()
                    }
                }
            }
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for shake detection
    }
}
