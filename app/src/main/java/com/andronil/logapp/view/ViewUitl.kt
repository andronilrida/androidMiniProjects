package com.andronil.logapp.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat


private const val CHANNEL_ID = "log_channel"
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun getNotificationManager(context: Context) = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

fun createNotificationChannel(context: Context, channelName : String): NotificationManager {
    val notificationManager = getNotificationManager(context)
    // Check if the device is running Android Oreo or higher
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Create a notification channel for Android Oreo and higher
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
        notificationManager.createNotificationChannel(channel)
    }
    return notificationManager
}

fun buildNotification(context: Context, title : String, text : String?,@DrawableRes icon : Int, intent: PendingIntent? = null): Notification {
    return NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(text)
        .setContentIntent(intent)
        .setAutoCancel(false)
        .setSmallIcon(icon).build()
}
