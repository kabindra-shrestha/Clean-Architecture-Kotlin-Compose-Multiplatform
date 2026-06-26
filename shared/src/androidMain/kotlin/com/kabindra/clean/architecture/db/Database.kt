package com.kabindra.clean.architecture.db

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import com.kabindra.clean.architecture.utils.appContext
import kotlinx.coroutines.Dispatchers

fun getDatabaseBuilder(): AppDatabase {
    val dbFile = appContext!!.getDatabasePath("hrm.db")
    return Room.databaseBuilder<AppDatabase>(context = appContext!!, name = dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        // Note: Remove allowMainThreadQueries() for production use.
        // Temporarily allow for debugging
        .allowMainThreadQueries()
        .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
        .build()
}