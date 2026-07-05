package com.kabindra.inappupdate

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
}

actual fun completeUpdate(playStoreUrl: String, appStoreUrl: String) {
}

actual fun exitApp() {
}