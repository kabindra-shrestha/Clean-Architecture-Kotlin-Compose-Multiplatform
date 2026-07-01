package com.kabindra.clean.architecture.db

import androidx.room.Room
import com.kabindra.clean.architecture.data.source.room.AppDatabase
import org.dany.worker.createSQLiteWasmWorker
import kotlinx.coroutines.Dispatchers

actual fun getDatabaseBuilder(): AppDatabase {
    return Room.inMemoryDatabaseBuilder<AppDatabase>()
        .setDriver(createSQLiteWasmWorker())
        .setQueryCoroutineContext(Dispatchers.Default)
        .build()
}
