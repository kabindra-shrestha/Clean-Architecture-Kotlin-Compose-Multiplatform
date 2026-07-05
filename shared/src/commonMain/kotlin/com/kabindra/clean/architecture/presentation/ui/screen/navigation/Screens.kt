package com.kabindra.clean.architecture.presentation.ui.screen.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute : NavKey {
    const val Route = "splash"
}

@Serializable
data object RegisterRoute : NavKey {
    const val Route = "register"
}

@Serializable
data object LoginRoute : NavKey {
    const val Route = "login"
}

@Serializable
data class LoginVerifyOTPRoute(
    val username: String,
    val appLoginCode: String,
) : NavKey {
    companion object {
        const val Route = "login_verify_otp"
    }
}

@Serializable
data object DashboardRoute : NavKey {
    const val Route = "dashboard"
}