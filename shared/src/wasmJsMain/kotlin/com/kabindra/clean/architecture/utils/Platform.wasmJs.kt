package com.kabindra.clean.architecture.utils

import web.navigator.navigator

class WasmPlatform : Platform {
    private val browserList = listOf("Chrome", "Firefox", "Safari", "Edge")
    override val userAgent = navigator.userAgent
    override val userDevice: String = userAgent.findAnyOf(browserList, ignoreCase = true)
        ?.let { (startIndex) -> userAgent.substring(startIndex).substringBefore(" ") }
        ?: "Unknown"
    override val devicePlatform: String = "WasmJs"
    override val deviceVersion: String = "1.0"
    override val deviceBuild: String = "1"
    override val deviceBrand: String = "Web"
    override val deviceModel: String = "Browser"
    override val appVersion: String = "1.0.0"
    override val appVersionCode: String = "1"
}

actual fun getPlatform(): Platform = WasmPlatform()