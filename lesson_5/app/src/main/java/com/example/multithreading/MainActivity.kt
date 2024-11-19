package com.example.multithreading

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.multithreading.databinding.ActivityMainBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureNanoTime
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        counter = intent.getIntExtra("counter_value", 0);
        updateCounterText(counter)

        binding.goToSecondActivity.setOnClickListener{
            startActivity(Intent(this, SecondActivity::class.java).putExtra("counter_value", counter))
        }

        binding.increaseButton.setOnClickListener {
            counter++
            val serviceIntent = Intent(this, CounterService::class.java)
            serviceIntent.putExtra("counter_value", counter)
            startService(serviceIntent)
        }

        binding.runCoroutine.setOnClickListener{
            Log.d("TAG", "onCreate() method called");
            val array = IntArray(1000000) { Random.nextInt(0, 1000000000) }
            lifecycleScope.launch{
                val msg = withContext(Dispatchers.Default) {
                    getQuickTime(array)
                }
                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateCounterText(counter: Int) {
        binding.counterText.text = getString(R.string.counter_text, counter)
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

    fun quickSort(arr: IntArray, left: Int = 0, right: Int = arr.size - 1): IntArray {
        var start = left
        var end = right
        val pivot = arr[(left + right) / 2]

        while (start <= end) {
            while (arr[start] < pivot) {
                start++
            }
            while (arr[end] > pivot) {
                end--
            }
            if (start <= end) {
                val temp = arr[start]
                arr[start] = arr[end]
                arr[end] = temp
                start++
                end--
            }
        }

        if (left < end) {
            quickSort(arr, left, end)
        }
        if (start < right) {
            quickSort(arr, start, right)
        }
        return arr
    }

    fun getQuickTime(arr: IntArray, print: Boolean = false): String{
        val quickSortTime = measureNanoTime {
            quickSort(arr)
            if(print){
                println("QuickSort result: ${arr.joinToString(" ")}")
            }
        }
        return ("QuickSort time: $quickSortTime ms")
    }
}