package com.lokal.otpauthapp.viewmodel

import java.time.LocalDateTime


sealed class ScreenState {
    object Login : ScreenState()
    object Otp : ScreenState()
    object Session : ScreenState()
}


data class AuthState(
    val currentScreen: ScreenState = ScreenState.Login,
    val email: String = "",
    val otpAttemptsLeft: Int = 0,
    val otpExpiryTime: LocalDateTime? = null,
    val sessionStartTime: LocalDateTime? = null,
    val errorMessage: String? = null,
    val debugOtp: String? = null // ✅ Debug OTP visible
)
