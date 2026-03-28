package com.kabindra.clean.architecture.presentation.ui.screen.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

enum class Screens(val title: String) {
    Splash(title = "Splash"),
    Login(title = "Login"),
    Home(title = "Home"),
}

@Serializable
data object SplashRoute : NavKey

@Serializable
data object RegisterRoute : NavKey

@Serializable
data object LoginRoute : NavKey

@Serializable
data class LoginVerifyOTPRoute(
    val username: String,
    val appLoginCode: String,
) : NavKey

@Serializable
data object DashboardRoute : NavKey