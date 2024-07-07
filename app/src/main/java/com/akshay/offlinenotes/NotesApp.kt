package com.akshay.offlinenotes

import android.app.Application
import androidx.room.Room
import com.akshay.offlinenotes.data.database.AppDatabase
import com.akshay.offlinenotes.utils.Constants

class NotesApp: Application() {
    companion object {
        lateinit var database: AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }
    }
