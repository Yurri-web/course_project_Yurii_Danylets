package com.example.lb_2_androidstudio_danylets

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class SecondActivity : AppCompatActivity() {
    private lateinit var counterTextView: TextView
    private var counter = 0
    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        counterTextView = findViewById(R.id.counterTextView)
        updateCounterDisplay()

        serviceIntent = Intent(this, CounterService::class.java)

        findViewById<Button>(R.id.incrementButton).setOnClickListener {
            counter++
            updateCounterDisplay()
            serviceIntent.putExtra("counter", counter)
            startService(serviceIntent)
        }

        findViewById<Button>(R.id.goToFragmentButton).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CounterFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun updateCounterDisplay() {
        counterTextView.text = counter.toString()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(counterReceiver, IntentFilter("CounterUpdate"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(counterReceiver)
    }

    private val counterReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            counter = intent?.getIntExtra("counter", 0) ?: 0
            updateCounterDisplay()
        }
    }
}
