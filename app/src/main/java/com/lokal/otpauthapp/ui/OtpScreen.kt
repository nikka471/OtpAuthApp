package com.lokal.otpauthapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lokal.otpauthapp.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun OtpScreen(viewModel: AuthViewModel) {
    val state by viewModel.authState.collectAsState()
    var otp by remember { mutableStateOf("") }
    var countdown by remember { mutableStateOf(60L) }

    LaunchedEffect(state.otpExpiryTime) {
        while (countdown > 0) {
            delay(1000)
            val remaining = state.otpExpiryTime?.let {
                Duration.between(LocalDateTime.now(), it).seconds
            } ?: 0
            countdown = remaining.coerceAtLeast(0)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter the OTP sent to ${state.email}",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(16.dp))


        state.debugOtp?.let { otpValue ->

            Text(
                text = "DEBUG OTP (for local testing only): $otpValue",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )


            Spacer(modifier = Modifier.height(12.dp))
        }


        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            placeholder = { Text("6-digit OTP") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Time left: $countdown sec",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )

        state.errorMessage?.let {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.validateOtp(otp) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Verify OTP",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { viewModel.resendOtp() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Resend OTP",
                fontSize = 16.sp
            )
        }
    }
}

