package com.example.taskmanager_project_mdad

import android.app.Service
import android.content.Intent
import android.os.Binder
import kotlinx.coroutines.*
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskBackgroundService : Service() {

    private val binder = LocalBinder()
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    private var isRunning = false
    private var updateJob: Job? = null
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    inner class LocalBinder : Binder() {
        fun getService(): TaskBackgroundService = this@TaskBackgroundService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startTaskUpdates()
        return START_STICKY
    }

    private fun startTaskUpdates() {
        if (!isRunning) {
            isRunning = true
            updateJob = serviceScope.launch {
                while (isRunning) {
                    _status.postValue("Checking for new tasks...")
                    Log.d("TaskUpdateService", "Checking for new tasks...")
                    // Simulate checking for new tasks
                    delay(5000) // Check every 5 seconds
                    _status.postValue("Updating database...")
                    Log.d("TaskUpdateService", "Updating database...")
                    // Simulate updating the database
                    withContext(Dispatchers.Main) {
                        // Update the database here (e.g., call a function in your repository)
                        // Example: repository.updateTasksFromRemote()
                    }
                    delay(2000)
                    _status.postValue("Idle")
                    Log.d("TaskUpdateService", "Idle")
                }
            }
        }
    }

    fun stopTaskUpdates() {
        isRunning = false
        updateJob?.cancel()
        _status.postValue("Stopped")
        Log.d("TaskUpdateService", "Stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTaskUpdates()
        serviceScope.cancel()
    }
}