package com.example.finalproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.models.Note
import com.example.finalproject.repositories.NotesRepository
import kotlinx.coroutines.launch

class NotesSharedViewModel(val repository: NotesRepository): ViewModel() {

    private val _selectedNote = MutableLiveData<Note?>()
    val selectedNote: LiveData<Note?> get() = _selectedNote

    val notes = repository.notes

    fun selectNote(note: Note?) {
        _selectedNote.value = note
    }

    fun clearSelectedNote() {
        _selectedNote.value = null
    }

    fun getAllNotes() = viewModelScope.launch {
        // aqui podriamos usar nuestros propios error codes
        repository.getAll().collect() {result ->
            if (!result) {
                // mostrar mensaje de error.
            }
        }
    }
}
