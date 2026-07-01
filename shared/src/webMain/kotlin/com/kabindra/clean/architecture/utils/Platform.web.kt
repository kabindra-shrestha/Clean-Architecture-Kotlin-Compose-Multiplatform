package com.kabindra.clean.architecture.utils

import kotlinx.browser.window

class WebPlatform : Platform {
    override val userAgent: String = window.navigator.userAgent
    override val userDevice: String = "Web Browser"

    override val devicePlatform: String = "web"
    override val deviceVersion: String = "1.0"
    override val deviceBuild: String = "1"
    override val deviceBrand: String = "Web"
    override val deviceModel: String = "Browser"
    override val appVersion: String = "1.0.0"
    override val appVersionCode: String = "1"
}

actual fun getPlatform(): Platform = WebPlatform()
