package com.akshay.offlinenotes.data.repository

import com.akshay.offlinenotes.data.database.NotesDao
import com.akshay.offlinenotes.data.model.NoteModel
import kotlinx.coroutines.flow.Flow


class NotesRepository(private val notesDao: NotesDao) {

    fun getAllNotes(): Flow<List<NoteModel>>? = notesDao.getAllNotes()

    fun getFilteredNotes(query: String): Flow<List<NoteModel>>? = notesDao.getFilteredNotes(query)

    suspend fun insertNote(note: NoteModel): Long = notesDao.insertNote(note)

    suspend fun deleteNote(note: NoteModel) = notesDao.deleteNote(note)

    suspend fun updateNote(note: NoteModel): Int = notesDao.updateNote(note)

}