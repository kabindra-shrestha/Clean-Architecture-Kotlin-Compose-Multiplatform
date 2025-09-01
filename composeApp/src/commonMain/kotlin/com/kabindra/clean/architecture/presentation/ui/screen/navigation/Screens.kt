package com.kabindra.clean.architecture.presentation.ui.screen.navigation

import kotlinx.serialization.Serializable

enum class Screens(val title: String) {
    Splash(title = "Splash"),
    Login(title = "Login"),
    Home(title = "Home"),
    Attendance(title = "Attendance"),
    Ticket(title = "Ticket"),
    Profile(title = "Profile"),
    Menu(title = "Menu"),
}

sealed class Route {
    @Serializable
    data object SplashRoute : Route()

    @Serializable
    data object LoginMainRoute : Route()

    @Serializable
    data object LoginRoute : Route()

    @Serializable
    data object LoginQrScannerRoute : Route()

    @Serializable
    data class LoginVerifyOTPRoute(
        val username: String,
        val appLoginCode: String,
    ) : Route()

    @Serializable
    data object MPINSetRoute : Route()

    @Serializable
    data object MPINVerifyRoute : Route()

    @Serializable
    data object DashboardRoute : Route()

    @Serializable
    data object HomeMainRoute : Route()

    @Serializable
    data object HomeRoute : Route()

    @Serializable
    data object ProfileRoute : Route()

    @Serializable
    data object NotificationRoute : Route()

    @Serializable
    data object AttendanceRoute : Route()

    @Serializable
    data class TicketRoute(
        val content: String? = "",
        val workflow: String? = "",
    ) : Route()

    @Serializable
    data object MenuRoute : Route()

}