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

**A Stranger Things inspired crisis communication app**

*When the lights flicker and the phones go dead, spell out your message the old way.*

### 📥 [Download APK](UpsideDownCommunicator.apk)

</div>

---

## ✨ Features

### 🎄 Christmas Lights Alphabet (Stranger Things Style)
- **Alphabet wall visualization** - Letters A-Z arranged like Joyce's Christmas lights
- **Multi-colored bulbs** - Red, Yellow, Green, Blue, Magenta, Orange, Cyan, Pink
- **Letter-by-letter transmission** - Watch each letter glow as your message is spelled out
- **Audio beeps** - 800Hz telegraph-style sounds accompany each letter

### 🧠 Mind Flayer Mode
| Feature | Description |
|---------|-------------|
| **Sanity Meter** | Drains 1% per second from 100% → 0% |
| **Possessed Mode** | Screen flips 180°, UI glitches, red corruption |
| **Recovery** | Shake device for instant recovery (with vibration) |
| **Auto-Recovery** | Wait 30 seconds if you can't shake |

### 📺 Retro 1983 Aesthetic
- CRT scanline overlay with subtle flicker
- Phosphor green (#33FF00) terminal colors  
- Monospace typography throughout
- Hard-edged buttons (no modern rounded corners)
- Animated splash screen with glowing logo

---

## 📱 Screenshots

| Splash Screen | Main Interface | Transmitting |
|---------------|----------------|--------------|
| Stranger Things style loading | Alphabet wall + Sanity meter | Letters light up sequentially |

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin |
| UI Framework | Jetpack Compose + Material3 |
| Architecture | MVVM with StateFlow |
| Audio | Synthesized sine wave (no external files) |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 36 |

---

## 📁 Project Structure

```
app/src/main/java/com/example/upsidedown/
├── MainActivity.kt                 # Entry point + splash navigation
├── data/
│   ├── MorseCodeEncoder.kt         # Text → Morse code
│   ├── MorseSoundPlayer.kt         # 800Hz beep synthesizer
│   ├── ShakeDetector.kt            # Accelerometer detection
│   └── CommunicatorViewModel.kt    # State management
└── ui/
    ├── theme/
    │   ├── Color.kt                # Retro palette (green/amber/red)
    │   ├── Type.kt                 # Monospace fonts
    │   └── Theme.kt                # Dark-only theme
    ├── components/
    │   ├── CRTOverlay.kt           # Scanline effect
    │   ├── SanityMeter.kt          # Animated progress bar
    │   └── SignalDisplay.kt        # Christmas lights alphabet
    └── screens/
        ├── SplashScreen.kt         # Animated logo screen
        └── MainScreen.kt           # Main communicator UI
```

---

## 🚀 Quick Start

### Build & Run
```bash
# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug
```

### Pre-built APK
Download `UpsideDownCommunicator.apk` from the repository root.

---

## 📖 How to Use

1. **Launch** - Watch the Stranger Things style splash screen
2. **Type Message** - Enter your message (e.g., "HELP")
3. **Transmit** - Press "ENCODE & TRANSMIT"
4. **Watch** - Each letter lights up on the alphabet wall with a beep
5. **Survive** - Monitor your sanity meter!
6. **Recover** - If possessed, **shake your phone** to restore sanity

---

## 🔑 Morse Code Reference

<details>
<summary>Click to expand full alphabet</summary>

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

</details>

---

## 🔐 Permissions

| Permission | Usage |
|------------|-------|
| `VIBRATE` | Haptic feedback on shake recovery |

---

## 🎬 Inspiration

Inspired by **Stranger Things Season 1** - the iconic scene where Joyce Byers uses Christmas lights to communicate with Will trapped in the Upside Down.

---

## 📄 License

MIT License - Use freely for your own interdimensional communication needs.

---

<div align="center">

*"Lights. That's how Will communicates."* - Joyce Byers

</div>
