package com.example.taskmanager_project_mdad

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat


class MusicService : Service() {
    private val TAG = "MusicService"

    private lateinit var mediaPlayer: MediaPlayer

    private val channelId = "music_service_channel"

    companion object {
        const val ACTION_START = "com.example.taskmanager_project_mdad.ACTION_START"
        const val ACTION_STOP = "com.example.taskmanager_project_mdad.ACTION_STOP"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // Not a bound service
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.bg_music_1)
        mediaPlayer.isLooping = true
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        when (intent?.action) {
            ACTION_START -> {

                startMusic()
            }
            ACTION_STOP -> {
                stopMusic()
            }
        }
        return START_NOT_STICKY
    }

    private fun startMusic() {

        if (!mediaPlayer.isPlaying) {

            mediaPlayer.start()
            startForeground(1, createNotification(isPlaying = true))
            Log.d("MusicService", "Music started")
        }

    }

    private fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.prepare()
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
            Log.d("MusicService", "Music stopped")
        }
    }


    private fun createNotification(isPlaying: Boolean): Notification {
        Log.d("MusicService", "Notification created")
        val playIntent = Intent(this, MusicService::class.java).apply {
            action = ACTION_START
        }
        val playPendingIntent = PendingIntent.getService(
            this,
            0,
            playIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = Intent(this, MusicService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this,
            1,
            stopIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val actionIntent = if (isPlaying) stopPendingIntent else playPendingIntent

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Music Now Playing")
            .setContentText("✨lofi✨ music")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                0,
                "Stop",
                stopPendingIntent
            )
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Music Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }




    override fun onDestroy() {
            super.onDestroy()
            stopMusic()
            mediaPlayer.release()
            Log.d("MusicService", "Service destroyed")
        }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopMusic()
        stopSelf()
        Log.d(TAG, "App removed from recent tasks, stopping service")
    }

    }