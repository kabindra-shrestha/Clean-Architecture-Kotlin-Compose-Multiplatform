package com.kabindra.inappupdate

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.Foundation.NSArray
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSDictionary
import platform.Foundation.NSJSONSerialization
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.UIApplication

@OptIn(ExperimentalForeignApi::class)
private suspend fun checkUpdateAvailable(): Boolean = withContext(Dispatchers.Default) {
    val bundle = NSBundle.mainBundle
    val info = bundle.infoDictionary ?: return@withContext false

    val identifier = info["CFBundleIdentifier"] as? String ?: return@withContext false
    val currentVersion = info["CFBundleShortVersionString"] as? String ?: return@withContext false
    val storeUrl = "https://itunes.apple.com/lookup?bundleId=$identifier"
    val url = NSURL.URLWithString(storeUrl) ?: return@withContext false
    val data = NSData.dataWithContentsOfURL(url) ?: return@withContext false

    val json = NSJSONSerialization.JSONObjectWithData(data, 0u, null) as? NSDictionary
        ?: return@withContext false
    val results = json.objectForKey("results") as? NSArray ?: return@withContext false
    if (results.count.toInt() == 0) return@withContext false

    val appStoreInfo = results.objectAtIndex(0u) as? NSDictionary ?: return@withContext false
    val storeVersion = appStoreInfo.objectForKey("version") as? String ?: return@withContext false

    // Compare versions
    val storeParts = storeVersion.split(".").mapNotNull { it.toIntOrNull() }
    val currentParts = currentVersion.split(".").mapNotNull { it.toIntOrNull() }

    for (i in 0 until maxOf(storeParts.size, currentParts.size)) {
        val storePart = storeParts.getOrElse(i) { 0 }
        val currentPart = currentParts.getOrElse(i) { 0 }
        if (storePart > currentPart) return@withContext true
        if (storePart < currentPart) return@withContext false
    }

    return@withContext false
}

actual fun checkUpdate(
    isForcedUpdate: Boolean,
    onReceiveVersionCode: (code: Int) -> Unit,
    onReceiveStalenessDays: (days: Int) -> Unit,
    onUpdateAvailable: (isForcedUpdate: Boolean) -> Unit,
    onUpdateNotAvailable: () -> Unit,
    onCancelled: (isForcedUpdate: Boolean) -> Unit,
    onFailed: (isForcedUpdate: Boolean) -> Unit,
    onDownloadProgress: (bytesDownloaded: Long, totalBytes: Long) -> Unit,
    onDownloadStart: () -> Unit,
    onDownloadComplete: () -> Unit
) {
    val mainScope = MainScope()

    mainScope.launch {
        try {
            val isAvailable = checkUpdateAvailable()
            if (isAvailable) {
                onUpdateAvailable(isForcedUpdate)
            } else {
                onUpdateNotAvailable()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onFailed(isForcedUpdate)
        }
    }
}

actual fun completeUpdate(playStoreUrl: String, appStoreUrl: String) {
    val appStoreUrl = appStoreUrl
    val url = NSURL.URLWithString(appStoreUrl) ?: return
    val sharedApp = UIApplication.sharedApplication
    sharedApp.openURL(
        url,
        options = emptyMap<Any?, Any?>(),
        completionHandler = null
    )
}

actual fun exitApp() {
}