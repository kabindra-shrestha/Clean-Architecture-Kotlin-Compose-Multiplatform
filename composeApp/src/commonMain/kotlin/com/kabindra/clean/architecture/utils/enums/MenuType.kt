package com.kabindra.clean.architecture.utils.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TimeToLeave
import androidx.compose.ui.graphics.vector.ImageVector
import com.kabindra.clean.architecture.presentation.ui.screen.navigation.Route

enum class MenuType(
    val title: String,
    val icon: ImageVector?,
    val slug: String,
    val route: Route?,
    val isDrawer: Boolean = false,
    val isBottomNavigation: Boolean = false
) {
    Home(
        "Home",
        Icons.Default.Dashboard,
        "home",
        Route.HomeRoute,
        isDrawer = true,
        isBottomNavigation = true
    ),
    Profile(
        "Profile",
        Icons.Default.Person,
        "profile",
        Route.ProfileRoute,
        isDrawer = true,
        isBottomNavigation = true
    ),
    AddTickets(
        "\nAdd\nTickets",
        null,
        "add-tickets",
        null,
        isDrawer = false,
        isBottomNavigation = true
    ),
    Attendance(
        "Attendance",
        Icons.Default.CalendarToday,
        "attendance",
        Route.AttendanceRoute,
        isDrawer = true,
        isBottomNavigation = true
    ),

    Tickets(
        "Tickets",
        Icons.Default.CalendarViewMonth,
        "tickets",
        Route.TicketRoute(),
        isDrawer = true,
        isBottomNavigation = true
    ),

    LeaveDetails(
        "Leave Details",
        Icons.Default.TimeToLeave,
        "leave-details",
        null,
        isDrawer = false,
        isBottomNavigation = false
    ),
    SearchEmployee(
        "Search Employee",
        Icons.Default.Search,
        "search-employee",
        null,
        isDrawer = false,
        isBottomNavigation = false
    ),
    HolidayList(
        "Holiday List",
        Icons.Default.EditCalendar,
        "holiday-list",
        null,
        isDrawer = false,
        isBottomNavigation = false
    ),
    Menu(
        "Menu",
        Icons.Default.Menu,
        "menu",
        null,
        isDrawer = false,
        isBottomNavigation = false
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