# 🔮 The Upside Down Communicator

<div align="center">

```
━━━━━━━━━━━━━━━━━━━━━━━━━━━
        T H E
   U P S I D E
      D O W N
━━━━━━━━━━━━━━━━━━━━━━━━━━━
     COMMUNICATOR v1.983
```

**A Stranger Things inspired Morse Code Transmitter**

*When the lights flicker and the phones go dead, signal your message the old way.*

### 📥 [Download APK](UpsideDownCommunicator.apk)

</div>

---

## 📖 How It Works (The Process)

This application turns your smartphone into a retro-style crisis communicator device. It converts standard text into Morse code signals using light and sound, while simulating the psychological stress of the "Upside Down" dimension.

### 1. The Input Phase
- **User Action**: You type a plain text message (e.g., "STAY HERE") into the terminal interface.
- **System**: The app accepts alphanumeric characters and standardizes them for transmission.

### 2. The Encoding Process
- **Logic**: The `MorseCodeEncoder` takes your text and converts it into a sequence of signal units based on International Morse Code standards.
- **Signal Units**:
  - **Dot (.)**: 1 unit duration
  - **Dash (-)**: 3 units duration
  - **Gaps**: Pauses between dots, letters, and words to ensure readability.

### 3. The Transmission
- **Visual**: A central signal light flashes in sync with the code.
  - **Bright Green Flash**: Active signal (Dot or Dash).
  - **Dim/Off**: Gaps/Silence.
- **Audio**: An 800Hz sine wave synthesizer beeps in perfect synchronization with the light flashes.
- **Status**: The terminal displays "TRANSMITTING..." and locks input until the message is complete.

### 4. The Mind Flayer Mechanic (Sanity System)
The app simulates the draining effect of the Upside Down environment:
- **Sanity Drain**: Your "Sanity Meter" drops by **1% every second** automatically.
- **The Corruption**:
  - **< 50%**: "INTERFERENCE DETECTED" warnings appear.
  - **< 20%**: "SYSTEM CRITICAL" alerts.
  - **0% (POSSESSED)**: The system becomes corrupted.
    - **Effect**: Transmission is blocked. The UI flickers red ("CorruptionRed"). Status changes to "SIGNAL CORRUPTED".
- **Recovery**:
  - **Manual**: Physically **shake your device** to snap out of possession and restore 100% sanity.
  - **Auto**: If you cannot shake, the system will reboot/recover automatically after 30 seconds.

---

## ✨ Features

- **Authentic Morse Engine**: Proper timing for dots (200ms), dashes (600ms), and spacing.
- **Retro 1983 Aesthetic**:
  - Phosphor green (#33FF00) terminal visuals.
  - CRT scanline effects.
  - Monospace typography.
- **Immersive Mechanics**: "Possession" mode requires physical interaction (shaking) to fix.
- **Reference Guide**: Built-in Morse code cheat sheet displayed on-screen.

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| **Language** | Kotlin |
| **UI** | Jetpack Compose (Material3) |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **State Management** | Kotlin Coroutines & StateFlow |
| **Audio** | AudioTrack API (Real-time Synthesis) |
| **Sensors** | Accelerometer (for Shake Detection) |

---

## 🚀 Quick Start

### Installation
1. Download `UpsideDownCommunicator.apk` from this repository.
2. Install on your Android device (Requires Android 7.0+).

### Usage
1. **Launch**: Wait for the retro boot sequence.
2. **Type**: Enter your distress signal.
3. **Transmit**: Tap `ENCODE & TRANSMIT`.
4. **Decipher**: Watch the flashing light or listen to the beeps. Use the on-screen key to decode.
5. **Survive**: Keep an eye on the Sanity Meter. **SHAKE** the device if it hits 0% to restore functionality.

---

## 📁 Project Structure

```
app/src/main/java/com/example/upsidedown/
├── data/
│   ├── CommunicatorViewModel.kt    # Core logic: State, Timers, Sanity Drain
│   ├── MorseCodeEncoder.kt         # Logic: String -> List<SignalUnit>
│   └── ShakeDetector.kt            # Sensor: Detects physical shaking
├── ui/
│   ├── components/
│   │   ├── SignalDisplay.kt        # UI: The flashing light & Morse legend
│   │   └── SanityMeter.kt          # UI: Progress bar & corruption effects
│   └── theme/                      # Styling: Colors, Type, Theme
```

---

<div align="center">

*"Friends don't lie... they transmit."*

</div>
