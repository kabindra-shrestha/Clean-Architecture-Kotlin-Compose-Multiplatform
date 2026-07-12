package com.kabindra.clean.architecture.presentation.ui.screen.dashboard

sealed class DashboardEvent {
    data object Load : DashboardEvent()
}
