package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _dashboardState = MutableStateFlow(DashboardState())

    val dashboardState = _dashboardState
        .onStart { onEvent(DashboardEvent.Load) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DashboardState()
        )

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.Load -> {
                loadDashboard()
            }
        }
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            _dashboardState.value = _dashboardState.value.copy(isLoading = true)
            delay(5000)
            _dashboardState.value = _dashboardState.value.copy(isLoading = false)
        }
    }
}
