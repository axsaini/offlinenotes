package com.akshay.offlinenotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akshay.offlinenotes.data.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
 abstract fun notesDao(): NotesDao
}