package com.kabindra.clean.architecture.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKString
import platform.Foundation.NSBundle
import platform.UIKit.UIDevice
import platform.posix.uname
import platform.posix.utsname

@OptIn(ExperimentalForeignApi::class)
fun getDeviceModelIdentifier(): String {
    return memScoped {
        val systemInfo = alloc<utsname>() // Allocate memory for utsname
        val result = uname(systemInfo.ptr) // Pass the pointer to uname

        if (result == 0) {
            systemInfo.machine.toKString() // Convert C string to Kotlin string
        } else {
            "" // Return "" if uname fails
        }
    }
}

fun getAppVersion(): String {
    return NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String
        ?: ""
}

fun getAppVersionCode(): String {
    return NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleVersion") as? String
        ?: ""
}

class IOSPlatform : Platform {
    override val userAgent: String = "IOS"
    override val userDevice: String =
        UIDevice.currentDevice.identifierForVendor!!.UUIDString
    override val devicePlatform: String = "ios"
    override val deviceVersion: String = UIDevice.currentDevice.systemVersion
    override val deviceBuild: String = UIDevice.currentDevice.systemVersion
    override val deviceBrand: String = UIDevice.currentDevice.name
    override val deviceModel: String = getDeviceModelIdentifier()
    override val appVersion: String = getAppVersion()
    override val appVersionCode: String = getAppVersionCode()
}

actual fun getPlatform(): Platform = IOSPlatform()