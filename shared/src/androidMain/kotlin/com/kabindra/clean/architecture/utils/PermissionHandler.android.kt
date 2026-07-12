package com.kabindra.clean.architecture.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

actual object PermissionHandler {

    private const val REQUEST_CODE = 2001

    @Volatile
    private var activity: Activity? = null

    private var callback: ((Boolean) -> Unit)? = null

    fun setActivity(activity: Activity) {
        this.activity = activity
    }

    actual fun requestPermission(
        permission: PermissionType,
        callback: (Boolean) -> Unit
    ) {

        val act = activity ?: run {
            callback(false)
            return
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            callback(true)
            return
        }

        val permissionName = Manifest.permission.POST_NOTIFICATIONS

        if (ContextCompat.checkSelfPermission(act, permissionName)
            == PackageManager.PERMISSION_GRANTED
        ) {
            callback(true)
            return
        }

        this.callback = callback

        ActivityCompat.requestPermissions(
            act,
            arrayOf(permissionName),
            REQUEST_CODE
        )
    }

    actual fun hasPermission(permission: PermissionType): Boolean {
        val act = activity ?: return false

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return ContextCompat.checkSelfPermission(
            act,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        grantResults: IntArray
    ) {
        if (requestCode != REQUEST_CODE) return

        val granted = grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED

        callback?.invoke(granted)
        callback = null
    }
}