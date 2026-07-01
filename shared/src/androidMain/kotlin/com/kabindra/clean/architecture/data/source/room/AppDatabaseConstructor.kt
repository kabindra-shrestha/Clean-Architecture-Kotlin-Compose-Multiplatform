package com.kabindra.clean.architecture.data.source.room

import androidx.room3.RoomDatabaseConstructor
import com.kabindra.clean.architecture.db.getDatabaseBuilder

// Manual actual for Android named to match original expect (AppDatabaseConstructor)
actual object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    actual override fun initialize(): AppDatabase = getDatabaseBuilder()
}

