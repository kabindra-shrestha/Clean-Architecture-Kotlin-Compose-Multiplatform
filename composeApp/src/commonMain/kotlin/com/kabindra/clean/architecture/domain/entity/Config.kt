package com.kabindra.clean.architecture.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val base_url: String? = "",
    val username: String? = "",
    val user_code: String? = "",
)
