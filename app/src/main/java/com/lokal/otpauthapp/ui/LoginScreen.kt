package com.lokal.otpauthapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lokal.otpauthapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    val isError = email.isBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()



            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Welcome Back!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Enter your email to receive a one-time password",
            fontSize = 16.sp,
            color = Color.Gray,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            singleLine = true,
            isError = isError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )


        if (isError) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Email cannot be empty",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = { viewModel.sendOtp(email) },
            enabled = !isError,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Send OTP",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


