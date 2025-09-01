package com.kabindra.clean.architecture.domain.repository.remote

import com.kabindra.clean.architecture.domain.entity.Dashboard
import com.kabindra.clean.architecture.utils.ktor.Result
import kotlinx.coroutines.flow.Flow


interface DashboardRepository {

    suspend fun getDashboard(): Flow<Result<Dashboard>>
}