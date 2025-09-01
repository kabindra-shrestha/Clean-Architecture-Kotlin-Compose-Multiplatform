package com.kabindra.inappupdate

import androidx.activity.ComponentActivity
import com.kabindra.inappupdate.UpdateManager.FlexibleUpdateDownloadListener

private lateinit var appContext: ComponentActivity

// Declare the UpdateManager
private lateinit var mUpdateManager: UpdateManager

private var isInAppUpdateStarted: Boolean = true

fun initializeUpdateManager(activity: ComponentActivity) {
    appContext = activity

    // Initialize the Update Manager with the Activity and the Update Mode
    if (!::mUpdateManager.isInitialized) {
        mUpdateManager = UpdateManager.builder(activity)!!
    }
}

private fun callFlexibleUpdate() {
    // Start a Flexible Update
    mUpdateManager.mode(UpdateManager.FLEXIBLE).start()
}

private fun callImmediateUpdate() {
    // Start a Immediate Update
    mUpdateManager.mode(UpdateManager.IMMEDIATE).start()
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
    // Callback from UpdateInfoListener
    // You can get the available version code of the apk in Google Play
    // Number of days passed since the user was notified of an update through the Google Play
    mUpdateManager.addUpdateInfoListener(object : UpdateManager.UpdateInfoListener {
        override fun onReceiveVersionCode(code: Int) {
            // You can show available version code here
            println("UpdateManager: onReceiveVersionCode $code")
            onReceiveVersionCode(code)
        }

        override fun onReceiveStalenessDays(days: Int) {
            // You can show staleness days here
            println("UpdateManager: onReceiveStalenessDays $days")
            onReceiveStalenessDays(days)
        }
    })

    // Callback for cancelled and failed
    mUpdateManager.addUpdateSuccessInfoListener(object :
        UpdateManager.UpdateSuccessInfoListener {
        override fun onUpdateAvailable() {
            println("UpdateManager: onUpdateAvailable")
            // onUpdateAvailable(isForcedUpdate)
        }

        override fun onUpdateNotAvailable() {
            println("UpdateManager: onUpdateNotAvailable")
            onUpdateNotAvailable()
        }
    })

    // Callback for cancelled and failed
    mUpdateManager.addUpdateErrorInfoListener(object : UpdateManager.UpdateErrorInfoListener {
        override fun onCancelled() {
            println("UpdateManager: onCancelled")
            onCancelled(isForcedUpdate)
        }

        override fun onFailed() {
            println("UpdateManager: onFailed")
            onFailed(isForcedUpdate)
        }
    })

    // Callback from Flexible Update Progress
    // This is only available for Flexible mode
    mUpdateManager.addFlexibleUpdateDownloadListener(object : FlexibleUpdateDownloadListener {
        override fun onDownloadProgress(bytesDownloaded: Long, totalBytes: Long) {
            // You can show download progress by $bytesDownloaded / $totalBytes
            println("UpdateManager: onDownloadProgress $bytesDownloaded / $totalBytes")
            onDownloadProgress(bytesDownloaded, totalBytes)
        }

        override fun onDownloadStart() {
            println("UpdateManager: onDownloadStart $isInAppUpdateStarted")
            if (isInAppUpdateStarted) {
                println("UpdateManager: onDownloadStart")
                onDownloadStart()
            }

            isInAppUpdateStarted = false
        }

        override fun onDownloadComplete() {
            println("UpdateManager: onDownloadComplete")
            onDownloadComplete()
        }
    })

    if (isForcedUpdate) {
        callImmediateUpdate()
    } else {
        callFlexibleUpdate()
    }
}

actual fun completeUpdate(playStoreUrl: String, appStoreUrl: String) {
    mUpdateManager.completeUpdate()
}

actual fun exitApp() {
    appContext.finishAffinity()
}