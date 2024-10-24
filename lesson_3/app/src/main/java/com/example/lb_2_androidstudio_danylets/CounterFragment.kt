package com.example.lb_2_androidstudio_danylets

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class CounterFragment : Fragment() {
    private var counter = 0
    private lateinit var counterTextView: TextView

    private val counterReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            counter = intent?.getIntExtra("counter", 0) ?: 0
            updateCounterDisplay()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_counter, container, false)
        counterTextView = view.findViewById(R.id.counterTextView)
        updateCounterDisplay()

        view.findViewById<Button>(R.id.incrementButton).setOnClickListener {
            counter++
            updateCounterDisplay()
            val serviceIntent = Intent(requireContext(), CounterService::class.java)
            serviceIntent.putExtra("counter", counter)
            requireContext().startService(serviceIntent)
        }

        return view
    }

    private fun updateCounterDisplay() {
        counterTextView.text = counter.toString()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(counterReceiver, IntentFilter("CounterUpdate"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(counterReceiver)
    }
}
