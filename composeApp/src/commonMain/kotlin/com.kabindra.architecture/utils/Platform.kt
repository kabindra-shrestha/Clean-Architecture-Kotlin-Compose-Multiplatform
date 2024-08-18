package com.kabindra.architecture.utils

interface Platform {
    val userAgent: String
    val userDevice: String
    val devicePlatform: String
    val deviceVersion: String
    val deviceBuild: String
    val deviceBrand: String
    val deviceModel: String
    val appVersion: String
    val appVersionCode: String
}

expect fun getPlatform(): Platform