package com.kabindra.clean.architecture.utils

import android.content.pm.PackageInfo
import android.os.Build
import android.provider.Settings

fun getAppVersion(): String {
    return runCatching {
        val packageInfo: PackageInfo =
            appContext?.packageManager!!.getPackageInfo(appContext!!.packageName, 0)
        packageInfo.versionName // Returns the version name
    }.getOrNull() ?: ""
}

fun getAppVersionCode(): String {
    return runCatching {
        val packageInfo: PackageInfo =
            appContext?.packageManager!!.getPackageInfo(appContext!!.packageName, 0)
        packageInfo.versionCode // Returns the version code
    }.getOrNull().toString()
}

class AndroidPlatform : Platform {
    override val userAgent: String = "Android"
    override val userDevice: String =
        Settings.Secure.getString(appContext!!.contentResolver, Settings.Secure.ANDROID_ID)

    override val devicePlatform: String = "android"
    override val deviceVersion: String = Build.VERSION.SDK_INT.toString()
    override val deviceBuild: String = Build.VERSION.SDK_INT.toString()
    override val deviceBrand: String = Build.BRAND
    override val deviceModel: String = Build.MODEL
    override val appVersion: String = getAppVersion()
    override val appVersionCode: String = getAppVersionCode()
}

actual fun getPlatform(): Platform = AndroidPlatform()