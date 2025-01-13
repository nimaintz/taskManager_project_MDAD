package com.example.taskmanager_project_mdad


import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlin.concurrent.thread

class BackgroundService : Service() {

    private val TAG = "BackgroundService"
    private var isCounting = true
    private var count = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //start task...
        Log.d(TAG, "Service started")
        startTask()
        return START_STICKY
    }

    private fun startTask() {
        Thread {
            startCounting()
            Log.d("Task", "log progress")
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        runOnUIThread {
            Toast.makeText(
                this,
                "Time spent in dark mode: $count seconds",
                Toast.LENGTH_LONG
            ).show()
        }
        Log.d(TAG, "Service stopped")
    }

    private fun startCounting(){
        while (isCounting) {
            count++
            Log.d(TAG, "Time spent in dark mode: $count")
            Thread.sleep(1000)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
    private fun runOnUIThread(runnable: Runnable) {
        Handler(Looper.getMainLooper()).post(runnable)
    }
}
