package com.kabindra.clean.architecture.domain.usecase.remote

import com.kabindra.clean.architecture.domain.entity.Dashboard
import com.kabindra.clean.architecture.domain.repository.remote.DashboardRepository
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow

class DashboardUseCase(private val repository: DashboardRepository) {
    suspend fun executeGetDashboard(): Flow<Result<Dashboard>> {
        return repository.getDashboard()
    }

}
