package com.example.multithreading

import android.app.Service
import android.content.Intent
import android.os.IBinder

class CounterService : Service() {
    private var _counter: Int = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        _counter++

        val broadcastIntent = Intent("com.example.UPDATE_COUNTER")
        broadcastIntent.putExtra("counter_value", _counter)
        sendBroadcast(broadcastIntent)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}