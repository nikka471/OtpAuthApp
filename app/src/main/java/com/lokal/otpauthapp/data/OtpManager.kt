package com.lokal.otpauthapp.data

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

data class OtpData(
    val otp: String,
    val expiryTime: LocalDateTime,
    var attemptsLeft: Int = 3
)

object OtpManager {

    private val otpMap = mutableMapOf<String, OtpData>()


    fun generateOtp(email: String): String {
        val otp = (100000..999999).random().toString()
        val expiryTime = LocalDateTime.now().plusSeconds(60)
        otpMap[email] = OtpData(otp, expiryTime, 3)
        return otp
    }


    fun validateOtp(email: String, inputOtp: String): Boolean {
        val otpData = otpMap[email] ?: return false


        if (LocalDateTime.now().isAfter(otpData.expiryTime)) {
            otpMap.remove(email)
            return false
        }


        return if (otpData.otp == inputOtp) {
            otpMap.remove(email)
            true
        } else {
            // Decrement attempts
            otpData.attemptsLeft -= 1
            if (otpData.attemptsLeft <= 0) {
                otpMap.remove(email)
            }
            false
        }
    }


    fun attemptsLeft(email: String): Int {
        return otpMap[email]?.attemptsLeft ?: 0
    }


    fun isExpired(email: String): Boolean {
        val otpData = otpMap[email] ?: return true
        return LocalDateTime.now().isAfter(otpData.expiryTime)
    }
}
