package com.example.database_lr

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.database_lr.sampledata.DatabaseInstance
import kotlinx.coroutines.*

class ShowStudentsActivity : AppCompatActivity() {
    private lateinit var tvStudentsInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_students)

        // Bind TextView to the layout
        tvStudentsInfo = findViewById(R.id.tvStudentsInfo)

        // Fetch and display students info
        displayStudentsInfo()
    }

    private fun displayStudentsInfo() {
        val database = DatabaseInstance.getDatabase(this)
        val dao = database.schoolDao()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Fetch students and groups in parallel
                val studentsDeferred = async { dao.getAllStudents() }
                val groupsDeferred = async { dao.getAllGroups() }

                val students = studentsDeferred.await()
                val groups = groupsDeferred.await()

                withContext(Dispatchers.Main) {
                    if (students.isEmpty()) {
                        tvStudentsInfo.text = "No students found."
                    } else {
                        val info = students.joinToString("\n") { student ->
                            val groupName = groups.find { it.id == student.groupId }?.groupName ?: "No group"
                            "${student.name} ${student.surname}, Age: ${student.age}, Group: $groupName"
                        }
                        tvStudentsInfo.text = info
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    tvStudentsInfo.text = "An error occurred while loading data: ${e.message}"
                }
            }
        }
    }
}
