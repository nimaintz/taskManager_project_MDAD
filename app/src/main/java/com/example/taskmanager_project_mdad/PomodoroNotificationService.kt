package com.example.taskmanager_project_mdad

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder

class PomodoroNotificationService : Service() {

    companion object {
        const val CHANNEL_ID = "PomodoroServiceChannel"
        const val NOTIFICATION_ID = 1
        const val ACTION_UPDATE = "ACTION_UPDATE"
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val message = intent?.getStringExtra(EXTRA_MESSAGE) ?: "Pomodoro Timer Running"

        // Update notification content
        updateNotification(message)

        return START_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Pomodoro Service",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)
    }

    private fun updateNotification(content: String) {
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Pomodoro Timer")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
