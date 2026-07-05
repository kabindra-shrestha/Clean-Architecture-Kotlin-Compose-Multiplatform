package com.kabindra.clean.architecture.data.source.room.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
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