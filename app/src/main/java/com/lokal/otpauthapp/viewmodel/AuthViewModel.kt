package com.lokal.otpauthapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lokal.otpauthapp.analytics.AnalyticsLogger
import com.lokal.otpauthapp.data.OtpManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    private var sessionTimerJob: Job? = null


    fun sendOtp(email: String) {
        val otp = OtpManager.generateOtp(email)
        AnalyticsLogger.logOtpGenerated(email)

        _authState.value = _authState.value.copy(
            currentScreen = ScreenState.Otp,
            email = email,
            otpAttemptsLeft = 3,
            otpExpiryTime = LocalDateTime.now().plusSeconds(60),
            errorMessage = null,
            debugOtp = otp
        )
    }


    fun validateOtp(inputOtp: String) {
        val email = _authState.value.email
        val otpExpired = OtpManager.isExpired(email)

        if (otpExpired) {

            _authState.value = _authState.value.copy(
                errorMessage = "OTP expired",
                otpAttemptsLeft = 0
            )
            AnalyticsLogger.logOtpFailure(email)
            return
        }

        val success = OtpManager.validateOtp(email, inputOtp)

        if (success) {
            AnalyticsLogger.logOtpSuccess(email)
            startSession()
        } else {

            val attemptsLeft = OtpManager.attemptsLeft(email)
            _authState.value = _authState.value.copy(
                otpAttemptsLeft = attemptsLeft,
                errorMessage = "Incorrect OTP. Attempts left: $attemptsLeft"
            )
            AnalyticsLogger.logOtpFailure(email)
        }
    }

    private fun startSession() {
        val email = _authState.value.email
        _authState.value = _authState.value.copy(
            currentScreen = ScreenState.Session,
            sessionStartTime = LocalDateTime.now(),
            errorMessage = null
        )

        sessionTimerJob?.cancel()
        sessionTimerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _authState.value = _authState.value.copy()
            }
        }
    }


    fun logout() {
        val email = _authState.value.email
        AnalyticsLogger.logLogout(email)
        sessionTimerJob?.cancel()
        _authState.value = AuthState()
    }


    fun resendOtp() {
        val email = _authState.value.email
        sendOtp(email)
    }
}
