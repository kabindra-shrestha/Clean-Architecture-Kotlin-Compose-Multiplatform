package com.kabindra.clean.architecture.presentation.ui.screen.navigation

import kotlinx.serialization.Serializable

enum class Screens(val title: String) {
    Splash(title = "Splash"),
    Login(title = "Login"),
    Home(title = "Home"),
}

sealed class Route {
    @Serializable
    data object SplashRoute : Route()

    @Serializable
    data object LoginMainRoute : Route()

    @Serializable
    data object RegisterRoute : Route()

    @Serializable
    data object LoginRoute : Route()

    @Serializable
    data class LoginVerifyOTPRoute(
        val username: String,
        val appLoginCode: String,
    ) : Route()

    @Serializable
    data object DashboardRoute : Route()

}