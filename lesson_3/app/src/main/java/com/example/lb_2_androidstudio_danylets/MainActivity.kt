package com.example.lb_2_androidstudio_danylets

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var counterTextView: TextView
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterTextView = findViewById(R.id.counterTextView)
        updateCounterDisplay()

        findViewById<Button>(R.id.goToSecondActivityButton).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    private fun updateCounterDisplay() {
        counterTextView.text = counter.toString()
    }
}
