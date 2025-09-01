package com.kabindra.clean.architecture.presentation.viewmodel.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabindra.clean.architecture.domain.entity.Dashboard
import com.kabindra.clean.architecture.domain.usecase.remote.DashboardUseCase
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(private val dashboardUseCase: DashboardUseCase) : ViewModel() {
    private val _dashboardState =
        MutableStateFlow<Result<Dashboard>>(Result.Initial)

    val dashboardState: StateFlow<Result<Dashboard>> get() = _dashboardState

    fun getDashboard() {
        viewModelScope.launch {
            dashboardUseCase.executeGetDashboard().collect { result ->
                _dashboardState.value = result
            }
        }
    }
}