package com.kabindra.clean.architecture.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "config")
@Serializable
data class ConfigDTO(
    @PrimaryKey
    @ColumnInfo(defaultValue = "")
    var base_url: String = ""
)

