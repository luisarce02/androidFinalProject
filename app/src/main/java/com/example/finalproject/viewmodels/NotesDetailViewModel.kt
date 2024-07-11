package com.example.finalproject.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.SharedPreferencesUserDataStore
import com.example.finalproject.models.Note
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class NotesDetailViewModel(val notesSharedViewModel: NotesSharedViewModel,
                           val userDataStore: SharedPreferencesUserDataStore
): ViewModel() {
    val repository = notesSharedViewModel.repository
    var isValid = MediatorLiveData<Boolean>()
    var deleteValid = MediatorLiveData<Boolean>()
    var mapValid = MediatorLiveData<Boolean>()
    var titulo = MutableLiveData<String>()
    var body = MutableLiveData<String>()
    var date = MutableLiveData<String>()
    var location = MutableLiveData<String>()
    var latitud = MutableLiveData<Double>()
    var longitud = MutableLiveData<Double>()
    var deletion = MediatorLiveData<Boolean>()


    init {
        isValid.addSource(titulo) {
            isValid.value = checkIfValid()
        }
        isValid.addSource(body) {
            isValid.value = checkIfValid()
        }

        notesSharedViewModel.selectedNote.observeForever { note ->
            deletion.value = note != null
            mapValid.value = note != null && location.value != null && latitud.value != 10.0 && longitud.value != -10.0
            updateTexts()
        }
    }

    fun updateTexts() {
        titulo.value = notesSharedViewModel.selectedNote.value?.titulo
        body.value = notesSharedViewModel.selectedNote.value?.body
        date.value = notesSharedViewModel.selectedNote.value?.fecha
        if (notesSharedViewModel.selectedNote.value?.latitud != 10.0 && notesSharedViewModel.selectedNote.value?.longitud != -10.0
            && notesSharedViewModel.selectedNote.value?.latitud != null){
            location.value = "${notesSharedViewModel.selectedNote.value?.latitud} ${notesSharedViewModel.selectedNote.value?.longitud}"
        } else {
            location.value = ""
        }
    }

    fun insert(note: Note) = viewModelScope.launch{
        repository.insertToApi(note) // ahora usando el del api
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun save() {
        if (notesSharedViewModel.selectedNote.value == null) {
            if (!(titulo.value).isNullOrBlank() && !(body.value).isNullOrBlank()) {
                insert(
                    Note(
                        id = "",
                        titulo = titulo.value!!,
                        latitud = latitud.value ?: 0.0,
                        longitud = longitud.value ?: 0.0,
                        user_id = userDataStore.getUserId(),
                        fecha = Date.from(Instant.now()).toString(),
                        body = body.value!!
                    )
                )
                clearFields()
            }
        } else {
            notesSharedViewModel.selectedNote.value?.let { note ->
                if (!(titulo.value).isNullOrBlank() && !(body.value).isNullOrBlank()) {
                    note.titulo = titulo.value!!
                    note.body = body.value!!
                    note.latitud = latitud.value ?: 10.0
                    note.longitud = longitud.value ?: -10.0
                    note.user_id = userDataStore.getUserId()
                    note.fecha = Date.from(Instant.now()).toString()
                    update(note)
                    clearFields()
                }
            }
        }
    }

    fun delete() {
        notesSharedViewModel.selectedNote.value?.let { note ->
            if (!(titulo.value).isNullOrBlank() && !(body.value).isNullOrBlank()) {
                deleteNote(note)
                clearFields()
            }
        }
    }

    private fun clearFields() {
        titulo.value = ""
        body.value = ""
        location.value = ""
        notesSharedViewModel.selectNote(null)
    }

    private fun checkIfValid() = !(titulo.value).isNullOrBlank() && !(body.value).isNullOrBlank()

    private fun checkForValidDelete() = deletion.value == true

    private fun checkForValidMapRedirect() = true
}
