# Upside Down Communicator

A retro-styled crisis communication Android app for transmitting messages when traditional methods fail. Built for scenarios where conventional text-based communication is compromised.

## Features

### 🔦 Cryptic Transmission Protocol
- Encodes messages into **Morse code visual signals**
- Screen flashes dots (short) and dashes (long)
- No plain text displayed - observers must decode visually
- Supports A-Z letters and 0-9 numbers

### 🧠 Mind Flayer Mode
- **Sanity Meter**: Drains from 100% to 0% over ~100 seconds
- **Possessed Mode**: Activates when sanity reaches 0
  - Screen flips 180° upside down
  - Visual glitch effects
  - UI becomes corrupted
- **Recovery**: Shake device OR wait 30 seconds for auto-recovery
- Haptic feedback on successful shake

### 📺 1983 Hardware Aesthetic
- CRT scanline overlay with subtle flicker
- Phosphor green (#33FF00) primary color
- Monospace terminal typography
- Chunky retro buttons with hard borders
- No rounded corners or modern effects

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose with Material3
- **Architecture**: MVVM with StateFlow
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36

## Project Structure

```
app/src/main/java/com/example/upsidedown/
├── MainActivity.kt              # Entry point, lifecycle management
├── data/
│   ├── MorseCodeEncoder.kt      # Text to Morse code conversion
│   ├── ShakeDetector.kt         # Accelerometer shake detection
│   └── CommunicatorViewModel.kt # State management
└── ui/
    ├── theme/
    │   ├── Color.kt             # Retro color palette
    │   ├── Type.kt              # Monospace typography
    │   └── Theme.kt             # Dark-only theme
    ├── components/
    │   ├── CRTOverlay.kt        # Scanline effect
    │   ├── SanityMeter.kt       # Progress bar with pulse
    │   └── SignalDisplay.kt     # Morse code flash area
    └── screens/
        └── MainScreen.kt        # Main communicator UI
```

## Building

```bash
# Debug build
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug
```

## Usage

1. **Enter Message**: Type your message in the input field (e.g., "SOS")
2. **Transmit**: Press "ENCODE & TRANSMIT"
3. **Decode**: Watch the flash pattern
   - Short flash = Dot (.)
   - Long flash = Dash (-)
4. **Survive**: Keep an eye on your sanity meter!
5. **Recover**: If possessed, shake the device to restore

## Morse Code Reference

| Letter | Code | Letter | Code |
|--------|------|--------|------|
| A | .- | N | -. |
| B | -... | O | --- |
| C | -.-. | P | .--. |
| D | -.. | Q | --.- |
| E | . | R | .-. |
| F | ..-. | S | ... |
| G | --. | T | - |
| H | .... | U | ..- |
| I | .. | V | ...- |
| J | .--- | W | .-- |
| K | -.- | X | -..- |
| L | .-.. | Y | -.-- |
| M | -- | Z | --.. |

## Permissions

- `VIBRATE` - Haptic feedback on shake recovery

## License

MIT License
