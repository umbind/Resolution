package com.resolution.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.resolution.app.ui.navigation.MainScaffold
import com.resolution.app.ui.screens.auth.AuthScreen
import com.resolution.app.ui.screens.onboarding.OnboardingScreen
import com.resolution.app.ui.theme.ResolutionTheme
import dagger.hilt.android.AndroidEntryPoint

enum class AppState {
    ONBOARDING,
    AUTH,
    MAIN
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResolutionTheme {
                ResolutionRootApp()
            }
        }
    }
}

@Composable
fun ResolutionRootApp() {
    var appState by remember { mutableStateOf(AppState.ONBOARDING) }
    var currentLanguage by remember { mutableStateOf("हिंदी") }
    var userRole by remember { mutableStateOf("parent") }

    when (appState) {
        AppState.ONBOARDING -> {
            OnboardingScreen(
                onFinishOnboarding = { appState = AppState.AUTH },
                onLanguageToggle = {
                    currentLanguage = if (currentLanguage == "हिंदी") "English" else "हिंदी"
                },
                currentLanguage = currentLanguage,
                modifier = Modifier.fillMaxSize()
            )
        }
        AppState.AUTH -> {
            AuthScreen(
                onAuthSuccess = { selectedRole ->
                    userRole = selectedRole
                    appState = AppState.MAIN
                },
                onContinueAsGuest = {
                    userRole = "guest"
                    appState = AppState.MAIN
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        AppState.MAIN -> {
            MainScaffold(
                onSignOut = { appState = AppState.AUTH },
                onLanguageChange = {
                    currentLanguage = if (currentLanguage == "हिंदी") "English" else "हिंदी"
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
