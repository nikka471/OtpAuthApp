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
import java.time.Duration
import java.time.LocalDateTime
import kotlinx.coroutines.delay

@Composable
fun SessionScreen(viewModel: AuthViewModel) {
    val state by viewModel.authState.collectAsState()
    var sessionDuration by remember { mutableStateOf("00:00") }

    LaunchedEffect(state.sessionStartTime) {
        while (state.currentScreen == viewModel.authState.value.currentScreen) {
            state.sessionStartTime?.let { start ->
                val elapsed = Duration.between(start, LocalDateTime.now())
                val minutes = elapsed.toMinutes().toString().padStart(2, '0')
                val seconds = (elapsed.seconds % 60).toString().padStart(2, '0')
                sessionDuration = "$minutes:$seconds"
            }
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Session Active",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Started At: ${state.sessionStartTime}",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Duration: $sessionDuration",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.logout() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Logout",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
