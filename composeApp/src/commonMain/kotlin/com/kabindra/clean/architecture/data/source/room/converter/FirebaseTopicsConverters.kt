package com.kabindra.clean.architecture.data.source.room.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

object FirebaseTopicsConverters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromMyDataClass(value: List<String>?): String? {
        return value?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toMyDataClass(value: String?): List<String>? {
        return value?.let { json.decodeFromString(it) }
    }
}