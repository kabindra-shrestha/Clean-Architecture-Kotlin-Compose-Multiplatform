package com.kabindra.inappupdate

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.IntentSender.SendIntentException
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import java.lang.ref.WeakReference

class UpdateManager private constructor(activity: ComponentActivity) : DefaultLifecycleObserver {

    private val mActivityWeakReference: WeakReference<ComponentActivity>

    // Creates instance of the manager.
    private val appUpdateManager: AppUpdateManager?

    // Returns an intent object that you use to check for an update.
    private val appUpdateInfoTask: Task<AppUpdateInfo>

    // Default mode is FLEXIBLE
    private var mode: Int = FLEXIBLE
    private var updateSuccessInfoListener: UpdateSuccessInfoListener? = null
    private var updateErrorInfoListener: UpdateErrorInfoListener? = null
    private var flexibleUpdateDownloadListener: FlexibleUpdateDownloadListener? = null

    private val listener: InstallStateUpdatedListener? =
        InstallStateUpdatedListener { installState ->
            if (installState.installStatus() == InstallStatus.DOWNLOADING) {
                println(TAG + "An update is downloading .....")
                val bytesDownloaded = installState.bytesDownloaded()
                val totalBytesToDownload = installState.totalBytesToDownload()
                println(TAG + "An update is downloading ....." + bytesDownloaded + " -- " + totalBytesToDownload)
                if (flexibleUpdateDownloadListener != null) {
                    flexibleUpdateDownloadListener!!.onDownloadProgress(
                        bytesDownloaded,
                        totalBytesToDownload
                    )
                }
                forUpdateDownloadStart()
            }
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                // After the update is downloaded, show a notification
                // and request user confirmation to restart the app.
                println(TAG + "An update has been downloaded.")
                forUpdateDownloadComplete()
            }
        }

    init {
        mActivityWeakReference = WeakReference(activity)
        appUpdateManager = AppUpdateManagerFactory.create(this.activity!!)
        appUpdateInfoTask = appUpdateManager.appUpdateInfo
        activity.lifecycle.addObserver(this)
    }

    companion object {
        private const val TAG = "InAppUpdateManager "

        const val FLEXIBLE = AppUpdateType.FLEXIBLE
        const val IMMEDIATE = AppUpdateType.IMMEDIATE

        private var instance: UpdateManager? = null

        fun builder(activity: ComponentActivity): UpdateManager? {
            if (instance == null) {
                instance = UpdateManager(activity)
            }
            println(TAG + "Instance created")
            return instance
        }
    }

    private val activity: Activity? get() = mActivityWeakReference.get()

    private val activityResultLauncher =
        activity.registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            val resultCode = result.resultCode

            handleResult(resultCode)
        }

    fun mode(mode: Int): UpdateManager {
        val strMode = if (mode == FLEXIBLE) "FLEXIBLE" else "IMMEDIATE"
        println(TAG + "Set update mode to : " + strMode)
        this.mode = mode
        return this
    }

    fun start() {
        if (mode == FLEXIBLE) {
            setUpListener()
        }
        checkUpdate()
    }

    private fun checkUpdate() {
        // Checks that the platform will allow the specified type of update.
        println(TAG + "Checking for updates")
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(mode)
            ) {
                // Request the update.
                println(TAG + "Update available")
                updateSuccessInfoListener!!.onUpdateAvailable()
                startUpdate(appUpdateInfo, activityResultLauncher)
            } else {
                println(TAG + "No Update available")
                updateSuccessInfoListener!!.onUpdateNotAvailable()
            }
        }.addOnFailureListener { e: Exception? ->
            // Handle the failure case
            println(TAG + "Failed to check for updates")
            println(TAG + e!!.message)

            // showManualUpdateDialog();
            updateSuccessInfoListener!!.onUpdateNotAvailable()
        }.addOnCompleteListener { appUpdateInfoTask ->

        }
    }

    private fun startUpdate(
        appUpdateInfo: AppUpdateInfo,
        activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        try {
            println(TAG + "Starting update")
            appUpdateManager!!.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                AppUpdateOptions.newBuilder(mode).build()
            )
        } catch (e: SendIntentException) {
            println(TAG + e.message)
        }
    }

    fun handleResult(resultCode: Int) {
        println(TAG + "Result code Update : " + resultCode)
        if (resultCode != RESULT_OK) {
            println(TAG + "Update flow failed! Result code: " + resultCode)
            // If the update is cancelled or fails,
            // you can request to start the update again.
            if (resultCode == RESULT_CANCELED) {
                if (updateErrorInfoListener != null) {
                    println(TAG + "Update flow cancelled!")
                    updateErrorInfoListener!!.onCancelled()
                }
            } else {
                if (updateErrorInfoListener != null) {
                    println(TAG + "Update flow failed!")
                    updateErrorInfoListener!!.onFailed()
                }
            }
        }
    }

    private fun continueUpdate() {
        if (instance!!.mode == FLEXIBLE) {
            continueUpdateForFlexible()
        } else {
            continueUpdateForImmediate()
        }
    }

    private fun continueUpdateForFlexible() {
        instance!!.appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            // If the update is still downloading,
            // notify the user an update is downloading.
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADING) {
                println(TAG + "An update is continue downloading .....")
                val bytesDownloaded = appUpdateInfo.bytesDownloaded()
                val totalBytesToDownload = appUpdateInfo.totalBytesToDownload()
                println(TAG + "An update is continue downloading ....." + bytesDownloaded + " -- " + totalBytesToDownload)
                if (flexibleUpdateDownloadListener != null) {
                    flexibleUpdateDownloadListener!!.onDownloadProgress(
                        bytesDownloaded,
                        totalBytesToDownload
                    )
                }
                instance!!.forUpdateDownloadStart()
            }
            // If the update is downloaded but not installed,
            // notify the user to complete the update.
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                println(TAG + "An update has been downloaded.")
                instance!!.forUpdateDownloadComplete()
            }
        }
    }

    private fun continueUpdateForImmediate() {
        instance!!.appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability()
                == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
            ) {
                // If an in-app update is already running, resume the update.
                try {
                    instance!!.appUpdateManager!!.startUpdateFlowForResult(
                        appUpdateInfo,
                        activityResultLauncher,
                        AppUpdateOptions.newBuilder(instance!!.mode).build()
                    )
                } catch (e: SendIntentException) {
                    println(TAG + e.message)
                }
            }
        }
    }

    fun completeUpdate() {
        completeUpdateForFlexible()
    }

    private fun completeUpdateForFlexible() {
        appUpdateManager!!.completeUpdate()
    }

    private fun forUpdateDownloadStart() {
        if (flexibleUpdateDownloadListener != null) {
            flexibleUpdateDownloadListener!!.onDownloadStart()
        }
    }

    private fun forUpdateDownloadComplete() {
        if (flexibleUpdateDownloadListener != null) {
            flexibleUpdateDownloadListener!!.onDownloadComplete()
        }
    }

    private fun setUpListener() {
        appUpdateManager!!.registerListener(listener!!)
    }

    fun addUpdateInfoListener(updateInfoListener: UpdateInfoListener) {
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                println(TAG + "Update available")
                val availableVersionCode = appUpdateInfo.availableVersionCode()
                val stalenessDays =
                    if (appUpdateInfo.clientVersionStalenessDays() != null) appUpdateInfo.clientVersionStalenessDays()!! else -1
                updateInfoListener.onReceiveVersionCode(availableVersionCode)
                updateInfoListener.onReceiveStalenessDays(stalenessDays)
            } else {
                println(TAG + "No Update available")
            }
        }
    }

    fun addUpdateSuccessInfoListener(updateSuccessInfoListener: UpdateSuccessInfoListener?) {
        this.updateSuccessInfoListener = updateSuccessInfoListener
    }

    fun addUpdateErrorInfoListener(updateErrorInfoListener: UpdateErrorInfoListener?) {
        this.updateErrorInfoListener = updateErrorInfoListener
    }

    fun addFlexibleUpdateDownloadListener(flexibleUpdateDownloadListener: FlexibleUpdateDownloadListener?) {
        this.flexibleUpdateDownloadListener = flexibleUpdateDownloadListener
    }

    private fun unregisterListener() {
        if (appUpdateManager != null && listener != null) {
            appUpdateManager.unregisterListener(listener)
            println(TAG + "Unregistered the install state listener")
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        continueUpdate()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)

        instance = null

        unregisterListener()
    }

    interface UpdateInfoListener {
        fun onReceiveVersionCode(code: Int)
        fun onReceiveStalenessDays(days: Int)
    }

    interface UpdateSuccessInfoListener {
        fun onUpdateAvailable()
        fun onUpdateNotAvailable()
    }

    interface UpdateErrorInfoListener {
        fun onCancelled()
        fun onFailed()
    }

    interface FlexibleUpdateDownloadListener {
        fun onDownloadProgress(bytesDownloaded: Long, totalBytes: Long)
        fun onDownloadStart()
        fun onDownloadComplete()
    }

}