package com.example.database_lr

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.database_lr.sampledata.DatabaseInstance
import com.example.database_lr.sampledata.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddStudentActivity : AppCompatActivity() {
    private lateinit var etStudentName: EditText
    private lateinit var etStudentSurname: EditText
    private lateinit var etStudentAge: EditText
    private lateinit var etGroupId: EditText
    private lateinit var btnSaveStudent: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        // Initialize views
        etStudentName = findViewById(R.id.etStudentName)
        etStudentSurname = findViewById(R.id.etStudentSurname)
        etStudentAge = findViewById(R.id.etStudentAge)
        etGroupId = findViewById(R.id.etGroupId)
        btnSaveStudent = findViewById(R.id.btnSaveStudent)

        val database = DatabaseInstance.getDatabase(this)
        val dao = database.schoolDao()

        btnSaveStudent.setOnClickListener {
            val name = etStudentName.text.toString().trim()
            val surname = etStudentSurname.text.toString().trim()
            val ageText = etStudentAge.text.toString().trim()
            val groupIdText = etGroupId.text.toString().trim()

            // Validate input
            if (name.isEmpty() || surname.isEmpty() || ageText.isEmpty() || groupIdText.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val age = ageText.toIntOrNull()
            val groupId = groupIdText.toIntOrNull()

            if (age == null || age <= 0) {
                Toast.makeText(this, "Please enter a valid age!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (groupId == null || groupId <= 0) {
                Toast.makeText(this, "Please enter a valid group ID!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert student into the database
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    dao.insertStudent(User(name = name, surname = surname, age = age, groupId = groupId))
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddStudentActivity, "Student added successfully!", Toast.LENGTH_SHORT).show()
                        finish() // Close activity after success
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddStudentActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
