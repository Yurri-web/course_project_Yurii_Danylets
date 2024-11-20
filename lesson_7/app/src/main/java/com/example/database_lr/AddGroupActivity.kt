package com.example.database_lr

import Group
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.database_lr.sampledata.DatabaseInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddGroupActivity : AppCompatActivity() {
    private lateinit var etGroupName: EditText
    private lateinit var btnSaveGroup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        // Initialize views
        etGroupName = findViewById(R.id.etGroupName)
        btnSaveGroup = findViewById(R.id.btnSaveGroup)

        val database = DatabaseInstance.getDatabase(this)
        val dao = database.schoolDao()

        btnSaveGroup.setOnClickListener {
            val groupName = etGroupName.text.toString().trim()

            // Validate input
            if (groupName.isEmpty()) {
                Toast.makeText(this, "Group name cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert group into the database
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    dao.insertGroup(Group(groupName = groupName))
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddGroupActivity, "Group added successfully!", Toast.LENGTH_SHORT).show()
                        finish() // Close the activity after success
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddGroupActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
