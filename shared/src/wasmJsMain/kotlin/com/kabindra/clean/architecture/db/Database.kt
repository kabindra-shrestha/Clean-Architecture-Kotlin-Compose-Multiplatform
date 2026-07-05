package com.kabindra.clean.architecture.db

import androidx.room3.Room
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import org.dany.worker.createSQLiteWasmWorker

actual fun getDatabaseBuilder(): AppDatabase {
    return Room.inMemoryDatabaseBuilder<AppDatabase>()
        .setDriver(createSQLiteWasmWorker())
        .setQueryCoroutineContext(Dispatchers.Default)
        .build()
}
