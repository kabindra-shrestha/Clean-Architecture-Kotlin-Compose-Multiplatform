package com.kabindra.clean.architecture.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.core.net.toUri
import com.kabindra.clean.architecture.utils.enums.FileExtensionType
import com.kabindra.clean.architecture.utils.enums.getFileExtensionType

actual fun getFileUri(filePath: String): Any {
    return "file://$filePath".toUri()  // Make sure it's a file URI
}

actual fun openFile(
    url: String,
    extension: String,
    onError: (String) -> Unit
) {
    openCustomTab(url, extension, onError = { onError(it) })
}

fun openCustomTab(url: String, extension: String, onError: (String) -> Unit) {
    val customTabsIntent = CustomTabsIntent.Builder().apply { }.build()

    // Check if Chrome is available and supports Custom Tabs
    val packageName = getChromePackageName()
    if (packageName != null) {
        // customTabsIntent.intent.setPackage(packageName)
        customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        customTabsIntent.launchUrl(appContext!!, url.toUri())
    } else {
        // Fallback: Open in a browser if Chrome is not available
        openInBrowserFallback(url, extension, onError = { onError(it) })
    }
}

private fun getChromePackageName(): String? {
    val packageManager = appContext!!.packageManager
    val customTabsPackages = packageManager.queryIntentServices(
        Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION), 0
    )

    for (info in customTabsPackages) {
        if (info.serviceInfo.packageName.equals("com.android.chrome", true)) {
            return info.serviceInfo.packageName
        }
    }
    return null
}

private fun openInBrowserFallback(url: String, extension: String, onError: (String) -> Unit) {
    val file = getFileExtensionType<FileExtensionType>(extension.lowercase())

    val mimeType = file.contentType

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(url.toUri(), mimeType)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    try {
        appContext!!.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        onError("No app found to open this file type. ${e.message}")
    }
}