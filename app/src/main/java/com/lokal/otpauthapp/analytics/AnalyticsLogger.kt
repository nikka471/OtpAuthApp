package com.lokal.otpauthapp.analytics

import timber.log.Timber

object AnalyticsLogger {

    fun init() {

        Timber.i("AnalyticsLogger initialized")
    }


    fun logOtpGenerated(email: String) {
        Timber.i("OTP generated for email: $email")
    }


    fun logOtpSuccess(email: String) {
        Timber.i("OTP validation SUCCESS for email: $email")
    }


    fun logOtpFailure(email: String) {
        Timber.w("OTP validation FAILURE for email: $email")
    }


    fun logLogout(email: String) {
        Timber.i("User logged out: $email")
    }
}
