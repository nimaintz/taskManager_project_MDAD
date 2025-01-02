package com.example.taskmanager_project_mdad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class CallReciver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                // Stop music when an incoming call is detected
                Log.d("CallReceiver", "Incoming call detected")
                val stopMusicIntent = Intent(context, MusicService::class.java).apply {
                    action = MusicService.ACTION_STOP
                }
                //context?.startService(stopMusicIntent)
                context?.startForegroundService(stopMusicIntent)
            }

        }

    }
}