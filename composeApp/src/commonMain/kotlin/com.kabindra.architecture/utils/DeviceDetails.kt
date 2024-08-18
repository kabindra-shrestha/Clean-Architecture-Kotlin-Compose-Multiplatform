package com.kabindra.architecture.utils

class DeviceDetails {
    private val platform = getPlatform()

    fun deviceDetails(): String {
        return "Device Details\n" +
                "${platform.userAgent}\n" +
                "${platform.userDevice}\n" +
                "${platform.devicePlatform}\n" +
                "${platform.deviceVersion}\n" +
                "${platform.deviceBuild}\n" +
                "${platform.deviceBrand}\n" +
                "${platform.deviceModel}\n" +
                "${platform.appVersion}\n" +
                "${platform.appVersionCode}\n"
    }
}