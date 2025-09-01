package com.kabindra.clean.architecture.di

import com.kabindra.clean.architecture.data.model.ConfigDTO
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BaseUrlProvider(private val appDatabase: AppDatabase) {
    private val mutex = Mutex()

    private var baseUrl: String? = null

    suspend fun getBaseUrl(): String = mutex.withLock {
        if (baseUrl.isNullOrEmpty()) {
            val baseUrls = appDatabase.configDao.findAll()
            baseUrl = baseUrls.firstOrNull()?.base_url?.takeIf { it.isNotEmpty() } ?: ""
        }
        baseUrl ?: ""
    }

    suspend fun updateBaseUrl(newBaseUrl: String) = mutex.withLock {
        baseUrl = newBaseUrl

        appDatabase.configDao.deleteAll() // Clear old base URLs if needed
        appDatabase.configDao.add(ConfigDTO(newBaseUrl)) // Add the new one
    }

    suspend fun clearBaseUrl() = mutex.withLock {
        baseUrl = ""

        appDatabase.configDao.deleteAll()
    }
}
