package com.kabindra.clean.architecture.data.model

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.kabindra.clean.architecture.domain.entity.ApiToken
import kotlinx.serialization.Serializable

@Entity(tableName = "apiToken")
@Serializable
data class ApiTokenDTO(
    @PrimaryKey
    @ColumnInfo(defaultValue = "")
    var token: String = "",
    @ColumnInfo(defaultValue = "")
    var refresh_token: String? = ""
)

fun ApiTokenDTO.toDomain(): ApiToken {
    return ApiToken(
        token = token,
        refresh_token = refresh_token
    )
}
