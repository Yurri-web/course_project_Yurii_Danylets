package com.example.database_lr.sampledata

import SchoolDatabase
import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    private var INSTANCE: SchoolDatabase? = null

    fun getDatabase(context: Context): SchoolDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                SchoolDatabase::class.java,
                "school_database"
            ).build()
        }
        return INSTANCE!!
    }
}