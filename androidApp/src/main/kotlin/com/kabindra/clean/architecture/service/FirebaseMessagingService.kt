package com.kabindra.clean.architecture.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kabindra.clean.architecture.MainActivity
import com.kabindra.clean.architecture.R
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
            sendNotification(remoteMessage)
        } else {
            sendNotification(remoteMessage)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("Firebase token", "New token: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        println("Firebase token send to server($token)")
    }

    private fun getBitmapFromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            println("Firebase image Error in getting notification image: " + e.localizedMessage)
            null
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = getString(R.string.fcm_default_notification_id).toInt()
        val channelId = getString(R.string.fcm_default_notification_channel_id)
        val channelName = getString(R.string.fcm_default_notification_channel_name)
        val channelDescription = getString(R.string.fcm_default_notification_channel_description)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val messageTitle = remoteMessage.notification?.title
        val messageBody = remoteMessage.notification?.body
        val imageUrl = remoteMessage.notification?.imageUrl?.toString().orEmpty()

        val type = remoteMessage.data["type"].orEmpty()
        val ticketId = remoteMessage.data["ticket_id"].orEmpty()
        val workflow = remoteMessage.data["workflow"].orEmpty()
        val date = remoteMessage.data["date"].orEmpty()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = channelDescription
            }

            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("ticket_id", ticketId)
        intent.putExtra("workflow", workflow)
        intent.putExtra("date", date)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT or
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        if (imageUrl.isNotEmpty()) {
            try {
                val bitmap: Bitmap = getBitmapFromUrl(imageUrl)!!
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(bitmap)
                ).setLargeIcon(bitmap)
            } catch (e: Exception) {
                notificationBuilder.setStyle(NotificationCompat.BigTextStyle())
            }
        } else {
            notificationBuilder.setStyle(NotificationCompat.BigTextStyle())
        }

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
