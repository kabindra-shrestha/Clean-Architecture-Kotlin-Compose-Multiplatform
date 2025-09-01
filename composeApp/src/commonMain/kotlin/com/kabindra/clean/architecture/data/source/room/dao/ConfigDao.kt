package com.kabindra.clean.architecture.data.source.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kabindra.clean.architecture.data.model.ConfigDTO

@Dao
interface ConfigDao {

    @Query("SELECT * FROM config")
    suspend fun findAll(): List<ConfigDTO>

    @Upsert
    suspend fun add(config: ConfigDTO)

    @Query("DELETE FROM config")
    suspend fun deleteAll()

}
