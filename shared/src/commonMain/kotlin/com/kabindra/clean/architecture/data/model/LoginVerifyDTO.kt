package com.kabindra.clean.architecture.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kabindra.clean.architecture.data.source.room.converter.FirebaseTopicsConverters
import com.kabindra.clean.architecture.domain.entity.ApiToken
import com.kabindra.clean.architecture.domain.entity.Features
import com.kabindra.clean.architecture.domain.entity.FeaturesUsed
import com.kabindra.clean.architecture.domain.entity.LoginUser
import com.kabindra.clean.architecture.domain.entity.LoginVerify
import com.kabindra.clean.architecture.domain.entity.User
import com.kabindra.clean.architecture.utils.base.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class LoginVerifyDTO(
    val response: LoginUserDTO?
) : BaseResponse()

@Serializable
data class LoginUserDTO(
    val token: String? = "",
    var refresh_token: String? = "",
    val user_details: UserDTO?,
    val features: FeaturesDTO?,
    val featuresUsed: FeaturesUsedDTO?
)

@Entity(tableName = "apiToken")
@Serializable
data class ApiTokenDTO(
    @PrimaryKey
    @ColumnInfo(defaultValue = "")
    var token: String = "",
    @ColumnInfo(defaultValue = "")
    var refresh_token: String? = ""
)

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
    @TypeConverters(FirebaseTopicsConverters::class)
    val firebase_topics: List<String> = listOf(),
)

@Serializable
data class FeaturesDTO(
    val otp: Boolean? = false,
    val mpin: Boolean? = false,
    val biometric: Boolean? = false
)

@Serializable
data class FeaturesUsedDTO(
    val otp: Boolean? = false,
    val mpin: Boolean? = false,
    val biometric: Boolean? = false,
)

// Mapper function
fun LoginVerifyDTO.toDomain(): LoginVerify {
    return LoginVerify(
        response = response?.toDomain(),
    ).apply {
        status = this@toDomain.status
        statusCode = this@toDomain.statusCode
        message = this@toDomain.message
    }
}

fun LoginUserDTO.toDomain(): LoginUser {
    return LoginUser(
        token = token,
        refresh_token = refresh_token,
        user_details = user_details?.toDomain(),
        features = features?.toDomain(),
        featuresUsed = featuresUsed?.toDomain()
    )
}

fun ApiTokenDTO.toDomain(): ApiToken {
    return ApiToken(
        token = token,
        refresh_token = refresh_token
    )
}

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
        firebase_topics = firebase_topics.map { it },
    )
}

fun FeaturesDTO.toDomain(): Features {
    return Features(
        otp = otp,
        mpin = mpin,
        biometric = biometric
    )
}

fun FeaturesUsedDTO.toDomain(): FeaturesUsed {
    return FeaturesUsed(
        otp = otp,
        mpin = mpin,
        biometric = biometric
    )
}

