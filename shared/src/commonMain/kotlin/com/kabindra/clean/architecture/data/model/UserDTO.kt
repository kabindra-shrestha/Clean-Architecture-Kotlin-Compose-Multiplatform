package com.kabindra.clean.architecture.data.model

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.kabindra.clean.architecture.domain.entity.User
import kotlinx.serialization.Serializable

@Entity(tableName = "users")
@Serializable
data class UserDTO(
    @ColumnInfo(defaultValue = "")
    val name: String? = "",
    @PrimaryKey
    @ColumnInfo(defaultValue = "")
    val code: String = "",
    @ColumnInfo(defaultValue = "")
    val department: String? = "",
    @ColumnInfo(defaultValue = "")
    val branch: String? = "",
    @ColumnInfo(defaultValue = "")
    val contact: String? = "",
    @ColumnInfo(defaultValue = "")
    val email: String? = "",
    @ColumnInfo(defaultValue = "")
    val username: String? = "",
    @ColumnInfo(defaultValue = "")
    val profile_picture: String? = "",
    @ColumnInfo(defaultValue = "[]")
    val firebase_topics: String = "[]",
)

fun UserDTO.toDomain(): User {
    return User(
        name = name,
        code = code,
        department = department,
        branch = branch,
        contact = contact,
        email = email,
        username = username,
        profile_picture = profile_picture,
        firebase_topics = try {
            val json = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            json.decodeFromString<List<String>>(firebase_topics)
        } catch (e: Exception) {
            emptyList()
        },
    )
}
