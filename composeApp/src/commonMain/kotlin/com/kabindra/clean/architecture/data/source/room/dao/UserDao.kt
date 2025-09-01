package com.kabindra.clean.architecture.data.source.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kabindra.clean.architecture.data.model.UserDTO

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    suspend fun findAll(): List<UserDTO>

    @Upsert
    suspend fun add(user: UserDTO)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

}