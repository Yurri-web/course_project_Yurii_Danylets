package com.example.multithreading

import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.content.IntentFilter
import androidx.activity.ComponentActivity
import com.example.multithreading.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private var counter: Int = 0
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        counter = intent.getIntExtra("counter_value", 0);
        updateCounterText(counter)

        binding.goToMainActivity.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java).putExtra("counter_value", counter))
        }

        binding.increaseButtonSecond.setOnClickListener {
            counter++

            val serviceIntent = Intent(this, CounterService::class.java)
            serviceIntent.putExtra("counter_value", counter)
            startService(serviceIntent)
        }
    }

    private fun updateCounterText(counter: Int) {
        binding.counterTextSecond.text = getString(R.string.counter_text, counter)
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter("com.example.UPDATE_COUNTER")
        registerReceiver(counterReceiver, filter, Context.RECEIVER_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(counterReceiver)
    }

    private val counterReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val newCounterValue = intent?.getIntExtra("counter_value", 0) ?: 0
            counter = newCounterValue
            updateCounterText(newCounterValue)
        }
    }
}