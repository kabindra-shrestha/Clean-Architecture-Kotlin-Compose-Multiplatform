package com.kabindra.clean.architecture.di

import com.kabindra.clean.architecture.data.source.room.AppDatabase
import com.kabindra.clean.architecture.db.getDatabaseBuilder
import org.koin.dsl.module

actual val platformModule = module {
    single<AppDatabase> { getDatabaseBuilder() }
}
