# Android Sandbox

Welcome to **Android Sandbox**! 🚀 

This project is a clean, beginner-friendly laboratory for exploring the core building blocks of Android development. Whether you're trying to understand how screens communicate, how to request permissions, or how to run background tasks, this app has a practical example for it.

## ✨ Features

This app acts as a toolkit demonstrating four major pillars of Android development:

* **🔐 Authentication & Local Storage (SharedPreferences)**
  * A fully functional mock login screen.
  * Automatically registers new users and validates returning users by saving credentials locally on the device.
* **🧭 Intents (Explicit & Implicit)**
  * Navigate smoothly between screens (Activities).
  * Request the OS to open third-party apps like the **Camera**, **Dialer**, and **Contacts/Phonebook** seamlessly.
* **📡 Broadcast Receivers (System Monitoring)**
  * A dynamic receiver that runs silently in the background.
  * Instantly detects when you toggle **Airplane Mode**.
  * Monitors real-time **Battery Percentage** and updates the UI instantly.
* **🎵 Background Services (MediaPlayer)**
  * A toggleable background service that plays the device's default ringtone.
  * Demonstrates how services can stay alive and manage resources even when you navigate away from the app.

## 🛠️ Tech Stack & Architecture
* **Language:** Kotlin
* **UI:** XML with ViewBinding and Edge-to-Edge layouts
* **Minimum SDK:** 24
* **Target SDK:** 34

## 🚀 Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Kiyotakakirito/Android-Sandbox.git
   ```
2. Open the project in **Android Studio**.
3. Let Gradle sync and download the necessary dependencies.
4. Hit **Run** (`Shift + F10`) to build and install the app on your emulator or physical device.

## 💡 Notes for Beginners
If you are learning Android, check out `MainActivity3.kt` and `DemoService.kt`. They contain highly commented, easy-to-read code explaining how Android's package visibility (`<queries>`) and `MediaPlayer` services work under the hood!

---
*Created as an experiment to explore and master Android core components.*
