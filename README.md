# Passwordless Authentication App (Email + OTP)

## Overview

This is a simple **passwordless authentication flow** built in **Kotlin** using **Jetpack Compose**, following a **ViewModel + UI State** architecture.  

The app allows a user to:
1. Enter an email and receive a **6-digit OTP**.
2. Validate the OTP to login.
3. Access a **session screen** showing login start time and live duration.
4. Logout from the session.

**All logic is implemented locally — no backend.**

---

## Features

### 1. Email + OTP Login
- User enters email.
- Taps "Send OTP".
- OTP is generated **locally** (6 digits).
- OTP expires in **60 seconds**.
- Maximum **3 attempts** allowed.
- Resending OTP invalidates previous OTP and resets attempts.
- OTP is stored per email using a `Map<String, OtpData>`.

### 2. OTP Rules
- Length: 6 digits.
- Expiry: 60 seconds.
- Max attempts: 3.
- Resending OTP:
  - Invalidates old OTP.
  - Resets attempts.

### 3. Session Screen
- Displays **session start time**.
- Shows **live duration** in `mm:ss`.
- Logout button stops timer and clears session.

### 4. Debug & Logging
- **DEBUG OTP** is shown in UI for local testing / screenshots.
- OTP is also logged using **Timber**.
- Events logged via Timber:
  - OTP generated
  - OTP validation success
  - OTP validation failure
  - Logout

**Note:** OTP display in UI is strictly for assignment/testing purposes. In production, OTP would be delivered via email/SMS.

---

## Data Structures

- `OtpData`:
```kotlin
data class OtpData(
    val otp: String,
    val expiryTime: LocalDateTime,
    var attemptsLeft: Int = 3
)


**Screens**
1. Login Screen: Email input, Send OTP button.
2. OTP Screen: OTP input, countdown timer, Verify & Resend buttons.
3. Session Screen: Session start time, duration timer, Logout button.
All screens have Material3 theming and proper padding/alignment.


**Usage**
1. Run the app in Android Studio.
2. Enter an email (e.g., test@example.com ).
3. Press Send OTP.
4. Check logcat for OTP ( DEBUG OTP ) or use displayed debug OTP.
5. Enter OTP to login.
6. Session screen shows session duration.
7. Press Logout to end session.


**Handling Edge Cases**
Expired OTP
Incorrect OTP
Maximum OTP attempts reached
Resend OTP resets attempts and expiry
Screen rotation preserves state using remember and ViewModel .

**GPT Assistance**
GPT used for:
Polishing UI with Jetpack Compose
Improving code readability & styling
Debug OTP display logic

**Understood & implemented manually:**

OTP generation & validation logic
Session timer with Live Duration
State management using ViewModel + Compose
Timber logging & event tracking
