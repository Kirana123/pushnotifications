package com.example.pushnotifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.btn)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        btn.setOnClickListener {

            val intent = Intent(this, AfterNotification::class.java)

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val contentView = RemoteViews(packageName, R.layout.activity_after_notification)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_baseline_notifications_active_24))
                    .setContentIntent(pendingIntent)
            } else {
                builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_baseline_notifications_active_24))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(12345, builder.build())
        }
    }
}