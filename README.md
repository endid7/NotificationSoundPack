# 🔊 SoundPack

**Indonesian Payment Notification Sound Library for Android**

[![Maven Central](https://img.shields.io/maven-central/v/io.github.endid7/soundpack.svg)](https://central.sonatype.com/artifact/io.github.endid7/soundpack)
[![GitHub Release](https://img.shields.io/github/v/release/endid7/NotificationSoundPack)](https://github.com/endid7/NotificationSoundPack/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

SoundPack is an Android library designed for payment applications that need Indonesian spoken transaction notifications.

It provides Indonesian number pronunciation, payment notification sounds, sequential audio playback, and an optional downloadable male voice pack.

---

## Features

- 🇮🇩 Indonesian number pronunciation
- 🔊 Female voice included directly in the library
- 👨 Optional male voice pack
- 💰 Indonesian Rupiah amount pronunciation
- 💳 Payment received notification sounds
- ⚡ Sequential audio playback
- 📦 Distributed through Maven Central
- 🔒 SHA-256 verification for the downloadable male voice pack
- 📱 Minimum Android SDK 21

---

## Installation

Add the dependency to your Android project:

```kotlin
dependencies {
    implementation("io.github.endid7:soundpack:1.1.2")
}
```

Make sure `mavenCentral()` is included in your repositories:

```kotlin
repositories {
    google()
    mavenCentral()
}
```

No GitHub credentials are required.

---

## Usage

### Import

```kotlin
import com.endi.soundpack.SoundPackPlayer
import com.endi.soundpack.model.Voice
```

### Select Voice

Use the female voice:

```kotlin
SoundPackPlayer.setVoice(Voice.FEMALE)
```

Use the male voice:

```kotlin
SoundPackPlayer.setVoice(Voice.MALE)
```

Get the currently selected voice:

```kotlin
val voice = SoundPackPlayer.getVoice()
```

---

## Play Amount

Play an Indonesian Rupiah amount:

```kotlin
SoundPackPlayer.playAmount(
    context = this,
    amount = 35000L
)
```

The number is converted into Indonesian pronunciation automatically:

```text
tiga puluh lima ribu rupiah
```

### Examples

```kotlin
SoundPackPlayer.playAmount(
    context = this,
    amount = 5000L
)
```

```text
lima ribu rupiah
```

```kotlin
SoundPackPlayer.playAmount(
    context = this,
    amount = 125000L
)
```

```text
seratus dua puluh lima ribu rupiah
```

Zero is supported:

```kotlin
SoundPackPlayer.playAmount(
    context = this,
    amount = 0L
)
```

```text
nol rupiah
```

Negative amounts are not supported.

---

## Payment Notification

Play the standard payment received notification:

```kotlin
SoundPackPlayer.playPaymentReceived(
    context = this
)
```

A completion callback can be used to chain sounds.

For example, play the payment notification first and then announce the amount:

```kotlin
SoundPackPlayer.playPaymentReceived(
    context = this,
    onComplete = {
        SoundPackPlayer.playAmount(
            context = this,
            amount = 35000L
        )
    }
)
```

The resulting flow is:

```text
Payment notification
        ↓
tiga puluh lima ribu rupiah
```

---

## Male Voice Pack

The female voice is included directly in the library.

The male voice pack is distributed separately and can be downloaded by the application when the user selects the male voice.

Check whether the male voice pack is available:

```kotlin
val available = SoundPackPlayer.isVoiceAvailable(
    context = this,
    voice = Voice.MALE
)
```

The application can use this check before allowing the user to switch to the male voice.

The male voice pack is downloaded from a GitHub Release and verified using SHA-256 before installation.

> The repository containing the voice pack must be publicly accessible so the Android application can download the release asset without GitHub credentials.

---

## Voice Pack Storage

Downloaded voice files are stored in the application's internal storage.

SoundPack manages downloading, extraction, validation, and storage of the male voice pack.

Applications using SoundPack do not need to manually extract or manage the MP3 files.

---

## Supported Number Components

The library includes audio components for Indonesian number pronunciation:

- nol
- satu
- dua
- tiga
- empat
- lima
- enam
- tujuh
- delapan
- sembilan
- sepuluh
- sebelas
- belas
- puluh
- ratus
- seratus
- ribu
- seribu
- juta
- miliar
- triliun
- rupiah

---

## Payment Sounds

The library includes payment notification sounds such as:

- Payment received
- Payment received – Indopay
- Payment received – MyIndopay

---

## Example Payment Flow

A typical payment notification can extract the amount from a transaction message and then play the payment notification followed by the amount:

```kotlin
val amount = data["amount"]
                ?.replace(",", "")
                ?.replace(".", "")
                ?.toLongOrNull()

if (amount != null) {
    SoundPackPlayer.playPaymentReceived(
        context = this,
        onComplete = {
            SoundPackPlayer.playAmount(
                context = this,
                amount = amount
            )
        }
    )
}
```

For example:

```text
Transaction message
        ↓
Rp. 35.000,00
        ↓
Payment notification
        ↓
tiga puluh lima ribu rupiah
```

---

## Playback Control

Stop the current playback:

```kotlin
SoundPackPlayer.stop()
```

Release the player:

```kotlin
SoundPackPlayer.release()
```

---

## Requirements

- Android SDK 21+
- Kotlin
- Maven Central

---

## Architecture

```text
SoundPack
├── Indonesian number conversion
├── Audio playback
├── Voice selection
├── Female voice resources
└── Male voice pack management
    ├── Download
    ├── SHA-256 verification
    ├── ZIP extraction
    └── Voice pack validation
```

---

## Maven Central

Latest published version:

```text
io.github.endid7:soundpack:1.1.2
```

[Maven Central](https://central.sonatype.com/artifact/io.github.endid7/soundpack)

---

## Repository

[GitHub Repository](https://github.com/endid7/NotificationSoundPack)

[Releases](https://github.com/endid7/NotificationSoundPack/releases)

---

## License

Apache License 2.0.

See [LICENSE](LICENSE) for details.

---

## Author

**Endi**

[GitHub](https://github.com/endid7)
