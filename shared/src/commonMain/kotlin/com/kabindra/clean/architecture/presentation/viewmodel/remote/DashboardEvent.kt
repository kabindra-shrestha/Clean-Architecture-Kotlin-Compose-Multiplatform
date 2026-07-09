package com.kabindra.clean.architecture.presentation.viewmodel.remote

sealed class DashboardEvent {
    data object Load : DashboardEvent()
}
