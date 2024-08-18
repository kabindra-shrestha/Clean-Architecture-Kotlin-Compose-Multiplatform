package com.kabindra.architecture.utils

import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val userAgent: String = "ios"
    override val userDevice: String = "ios"
    override val devicePlatform: String = "ios"
    override val deviceVersion: String = UIDevice.currentDevice.systemVersion
    override val deviceBuild: String = UIDevice.currentDevice.systemVersion
    override val deviceBrand: String = UIDevice.currentDevice.name
    override val deviceModel: String = UIDevice.currentDevice.model
    override val appVersion: String = ""
    override val appVersionCode: String = ""
}

actual fun getPlatform(): Platform = IOSPlatform()