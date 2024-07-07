package com.akshay.offlinenotes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshay.offlinenotes.NotesApp
import com.akshay.offlinenotes.data.model.NoteModel
import com.akshay.offlinenotes.data.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {
    private var notesRepository = NotesRepository(NotesApp.database.notesDao())
    private val _operationStatus = MutableLiveData<Long>()

    fun getNotes(query: String?): Flow<List<NoteModel>>? =
        if (query == null) notesRepository.getAllNotes() else notesRepository.getFilteredNotes(query)

    fun deleteNote(note: NoteModel) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
        }
    }

    fun updateNote(note: NoteModel) {
        viewModelScope.launch {
            val status = notesRepository.updateNote(note).toLong()
            _operationStatus.postValue(status)
        }
    }

    fun insertNote(note: NoteModel) {
        viewModelScope.launch {
            val status = notesRepository.insertNote(note)
            _operationStatus.postValue(status)
        }
    }

    fun operationStatus(): LiveData<Long> {
        return _operationStatus
    }
}