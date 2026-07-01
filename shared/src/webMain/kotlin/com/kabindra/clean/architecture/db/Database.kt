package com.kabindra.clean.architecture.db

import com.kabindra.clean.architecture.data.source.room.AppDatabase

expect fun getDatabaseBuilder(): AppDatabase
