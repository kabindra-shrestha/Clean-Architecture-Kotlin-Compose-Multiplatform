package com.kabindra.clean.architecture.data.source.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
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