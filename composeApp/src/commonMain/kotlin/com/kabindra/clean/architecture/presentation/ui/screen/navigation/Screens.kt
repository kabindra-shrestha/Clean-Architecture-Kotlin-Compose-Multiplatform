package com.kabindra.clean.architecture.presentation.ui.screen.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

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
