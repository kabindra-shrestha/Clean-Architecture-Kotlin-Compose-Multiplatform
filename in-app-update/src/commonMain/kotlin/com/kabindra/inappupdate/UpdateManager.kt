package com.kabindra.inappupdate

expect fun checkUpdate(
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
)

expect fun completeUpdate(playStoreUrl: String, appStoreUrl: String)

expect fun exitApp()