package com.kabindra.clean.architecture.data.source.room

import androidx.room3.ConstructedBy
import androidx.room3.Database
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import com.kabindra.clean.architecture.data.model.ApiTokenDTO
import com.kabindra.clean.architecture.data.model.UserDTO
import com.kabindra.clean.architecture.data.source.room.dao.ApiTokenDao
import com.kabindra.clean.architecture.data.source.room.dao.UserDao

@Database(
    entities = [ApiTokenDTO::class, UserDTO::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apiTokenDao(): ApiTokenDao
    abstract fun userDao(): UserDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}