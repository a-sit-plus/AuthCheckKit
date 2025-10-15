<div align="center">
<picture>
  <source media="(prefers-color-scheme: dark)" srcset="logo-dark.png">
  <source media="(prefers-color-scheme: light)" srcset="logo-light.png">
  <img class="logo" alt="AuthCheckKit – Kotlin Multiplatform authentication capabilities check" src="logo-light.png">
</picture>

# Multiplatform authentication capabilities check
<img src="https://raw.githubusercontent.com/a-sit-plus/a-sit-plus.github.io/709e802b3e00cb57916cbb254ca5e1a5756ad2a8/A-SIT%20Plus_%20official_opt.svg" alt="A-SIT Plus Official" />
<img src="https://img.shields.io/badge/license-Apache%20License%202.0-brightgreen.svg?style=flat" alt="GitHub license" />
<img src="https://img.shields.io/badge/kotlin-multiplatform--mobile-orange.svg?logo=kotlin" alt="Kotlin" />
<img src="https://img.shields.io/badge/kotlin-2.2.10-blue.svg?logo=kotlin" alt="Kotlin" />
<img src="https://img.shields.io/maven-central/v/at.asitplus/authcheckkit" alt="Maven" />
</div>

AuthCheckKit is a Kotlin Multiplatform library designed to seamlessly assess the authentication capabilities of iOS and Android devices. Whether you're building secure apps or tailoring user experiences based on device-level security, AuthCheckKit provides a unified API to answer critical questions like:

* ✅ Is a screen lock (PIN, pattern, password) configured?

* 🧬 Does the device support biometric authentication (e.g., fingerprint, face recognition)?

* 🔍 Is biometric authentication currently available and enrolled?

Built with multiplatform flexibility in mind, AuthCheckKit abstracts platform-specific security checks into a clean, idiomatic Kotlin interface—so you can write once and deploy confidently across mobile ecosystems.

## Usage

Declare the dependency in the common source set:

```kotlin 
sourceSets {
    commonMain.dependencies {
        //...
        implementation("at.asitplus.authcheckkit:core:$version")
    }
}
```

Use the library in your common code:

```kotlin 
val deviceStatus = AuthCheckKit.getDeviceStatus()
```

## Contributing
External contributions are greatly appreciated!
Just be sure to observe the contribution guidelines (see [CONTRIBUTING.md](CONTRIBUTING.md)).

---
<p align="center">
The Apache License does not apply to the logos, (including the A-SIT logo) and the project/module name(s), as these are the sole property of
A-SIT/A-SIT Plus GmbH and may not be used in derivative works without explicit permission!
</p>