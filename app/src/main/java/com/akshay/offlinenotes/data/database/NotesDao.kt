package com.akshay.offlinenotes.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.akshay.offlinenotes.data.model.NoteModel
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteModel): Long

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteModel>>?

    @Query("SELECT * FROM notes WHERE title LIKE :query OR description LIKE :query OR category LIKE :query")
    fun getFilteredNotes(query: String): Flow<List<NoteModel>>?

    @Delete
    suspend fun deleteNote(note: NoteModel)

    @Update
    suspend fun updateNote(note: NoteModel): Int
}