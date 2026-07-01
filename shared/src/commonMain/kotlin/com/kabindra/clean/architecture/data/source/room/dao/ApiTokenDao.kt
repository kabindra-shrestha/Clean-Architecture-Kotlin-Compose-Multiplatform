package com.kabindra.clean.architecture.data.source.room.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.kabindra.clean.architecture.data.model.ApiTokenDTO

@Dao
interface ApiTokenDao {

    @Query("SELECT * FROM apiToken")
    suspend fun findAll(): List<ApiTokenDTO>

    @Upsert
    suspend fun add(apiToken: ApiTokenDTO)

    @Query("DELETE FROM apiToken")
    suspend fun deleteAll()

}