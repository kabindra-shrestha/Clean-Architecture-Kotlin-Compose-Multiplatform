package com.kabindra.clean.architecture.data.source.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.kabindra.clean.architecture.data.model.ApiTokenDTO
import com.kabindra.clean.architecture.data.model.ConfigDTO
import com.kabindra.clean.architecture.data.model.UserDTO
import com.kabindra.clean.architecture.data.source.room.converter.FirebaseTopicsConverters
import com.kabindra.clean.architecture.data.source.room.dao.ApiTokenDao
import com.kabindra.clean.architecture.data.source.room.dao.ConfigDao
import com.kabindra.clean.architecture.data.source.room.dao.UserDao

@Database(
    entities = [ConfigDTO::class, ApiTokenDTO::class, UserDTO::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(
    FirebaseTopicsConverters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract val configDao: ConfigDao
    abstract val apiTokenDao: ApiTokenDao
    abstract val userDao: UserDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
