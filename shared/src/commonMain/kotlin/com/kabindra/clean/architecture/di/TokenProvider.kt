package com.kabindra.clean.architecture.di

import com.kabindra.clean.architecture.data.model.ApiTokenDTO
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class TokenProvider(private val appDatabase: AppDatabase) {
    private val mutex = Mutex()

    private var accessToken: String? = null
    private var refreshToken: String? = null

    suspend fun getAccessToken(): String = mutex.withLock {
        if (accessToken.isNullOrEmpty()) {
            val tokens = appDatabase.apiTokenDao().findAll()
            accessToken = tokens.firstOrNull()?.token?.takeIf { it.isNotEmpty() } ?: ""
        }
        accessToken ?: ""
    }

    suspend fun getRefreshToken(): String = mutex.withLock {
        if (refreshToken.isNullOrEmpty()) {
            val tokens = appDatabase.apiTokenDao().findAll()
            refreshToken = tokens.firstOrNull()?.refresh_token?.takeIf { it.isNotEmpty() } ?: ""
        }
        refreshToken ?: ""
    }

    suspend fun updateTokens(newAccessToken: String, newRefreshToken: String) = mutex.withLock {
        accessToken = newAccessToken
        refreshToken = newRefreshToken

        appDatabase.apiTokenDao().deleteAll() // Clear old tokens if needed
        appDatabase.apiTokenDao().add(ApiTokenDTO(newAccessToken, newRefreshToken)) // Add the new one
    }

    suspend fun clearTokens() = mutex.withLock {
        accessToken = ""
        refreshToken = ""

        appDatabase.apiTokenDao().deleteAll()
    }
}