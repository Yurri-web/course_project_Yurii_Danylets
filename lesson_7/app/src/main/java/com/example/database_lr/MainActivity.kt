package com.example.database_lr

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.database_lr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listeners using binding
        binding.btnAddGroup.setOnClickListener {
            startActivity(Intent(this, AddGroupActivity::class.java))
        }

        binding.btnAddStudent.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }

        binding.btnShowStudents.setOnClickListener {
            startActivity(Intent(this, ShowStudentsActivity::class.java))
        }
    }
}
