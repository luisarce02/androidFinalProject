package com.example.finalproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.models.Note
import com.example.finalproject.repositories.NotesRepository
import kotlinx.coroutines.launch

class NotesSharedViewModel(val repository: NotesRepository): ViewModel() {

    var selectedNote: Note? = null

    val notes = repository.notes

    fun selectNote(note: Note) {
        selectedNote = note
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
