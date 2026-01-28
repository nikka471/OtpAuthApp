package com.lokal.otpauthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lokal.otpauthapp.ui.LoginScreen
import com.lokal.otpauthapp.ui.OtpScreen
import com.lokal.otpauthapp.ui.SessionScreen
import com.lokal.otpauthapp.ui.theme.OtpAuthAppTheme
import com.lokal.otpauthapp.viewmodel.AuthViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(authViewModel: AuthViewModel = viewModel()) {
    val state = authViewModel.authState.collectAsState().value

    OtpAuthAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            when (state.currentScreen) {
                com.lokal.otpauthapp.viewmodel.ScreenState.Login -> LoginScreen(authViewModel)
                com.lokal.otpauthapp.viewmodel.ScreenState.Otp -> OtpScreen(authViewModel)
                com.lokal.otpauthapp.viewmodel.ScreenState.Session -> SessionScreen(authViewModel)
            }
        }
    }
}


