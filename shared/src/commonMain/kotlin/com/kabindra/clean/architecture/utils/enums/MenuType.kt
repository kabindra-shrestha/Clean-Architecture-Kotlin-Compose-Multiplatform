package com.kabindra.clean.architecture.utils.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey

enum class MenuType(
    val title: String,
    val icon: ImageVector?,
    val slug: String,
    val route: NavKey?,
    val isDrawer: Boolean = false,
    val isBottomNavigation: Boolean = false
) {
    Home(
        "Home",
        Icons.Default.Dashboard,
        "home",
        null,
        isDrawer = true,
        isBottomNavigation = true
    ),
    Profile(
        "Profile",
        Icons.Default.Person,
        "profile",
        null,
        isDrawer = true,
        isBottomNavigation = true
    ),
    Settings(
        "Settings",
        Icons.Default.Settings,
        "settings",
        null,
        isDrawer = false,
        isBottomNavigation = false
    ),
    Logout(
        "Logout",
        Icons.AutoMirrored.Filled.Logout,
        "logout",
        null,
        isDrawer = true,
        isBottomNavigation = false
    ),
}

inline fun <reified T : Enum<T>> getMenuType(slug: String): MenuType {
    return enumValues<T>().find { (it as MenuType).slug == slug } as MenuType
}