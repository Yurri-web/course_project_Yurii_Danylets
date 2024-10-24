package com.example.lb_2_androidstudio_danylets

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class CounterService : Service() {
    private var counter = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            counter = it.getIntExtra("counter", 0)
            val broadcastIntent = Intent("CounterUpdate")
            broadcastIntent.putExtra("counter", counter)
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
