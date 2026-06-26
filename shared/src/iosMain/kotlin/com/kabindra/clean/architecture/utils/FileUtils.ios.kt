package com.kabindra.clean.architecture.utils

import io.ktor.http.encodeURLPath
import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIApplication

actual fun getFileUri(filePath: String): Any {
    return NSURL.fileURLWithPath(filePath)  // Ensure it's a proper file URL
}

actual fun openFile(
    url: String,
    extension: String,
    onError: (String) -> Unit
) {
    val nsUrl = NSURL.URLWithString(url.encodeURLPath()) ?: return
    val safariVC = SFSafariViewController(nsUrl)

    val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootVC?.presentViewController(safariVC, animated = true, completion = null)

    // UIApplication.sharedApplication.openURL(nsUrl)
}