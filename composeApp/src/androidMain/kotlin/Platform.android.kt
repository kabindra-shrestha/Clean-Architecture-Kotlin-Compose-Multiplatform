import android.os.Build

class AndroidPlatform : Platform {
    override val userAgent: String = "android"
    override val userDevice: String = "android"
    override val devicePlatform: String = "android"
    override val deviceVersion: String = Build.VERSION.SDK_INT.toString()
    override val deviceBuild: String = Build.VERSION.SDK_INT.toString()
    override val deviceBrand: String = Build.BRAND
    override val deviceModel: String = Build.MODEL
    override val appVersion: String = ""
    override val appVersionCode: String = ""
}

actual fun getPlatform(): Platform = AndroidPlatform()