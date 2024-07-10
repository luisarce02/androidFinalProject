package com.example.finalproject.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.models.Note
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class NotesDetailViewModel(val notesSharedViewModel: NotesSharedViewModel,
                           val userId: String?, ): ViewModel() {
    val repository = notesSharedViewModel.repository
    var isValid = MediatorLiveData<Boolean>()

    var titulo = MutableLiveData<String>()
    var body = MutableLiveData<String>()
    var date = MutableLiveData<String>()
    var location = MutableLiveData<String>()
    var latitud = MutableLiveData<Double>()
    var longitud = MutableLiveData<Double>()


    init {
        isValid.addSource(titulo) {
            isValid.value = checkIfValid()
        }
        isValid.addSource(body) {
            isValid.value = checkIfValid()
        }
    }

    fun updateTexts() {
        titulo.value = notesSharedViewModel.selectedNote?.titulo
        body.value = notesSharedViewModel.selectedNote?.body
        date.value = notesSharedViewModel.selectedNote?.fecha
        location.value = "${notesSharedViewModel.selectedNote?.latitud} ${notesSharedViewModel.selectedNote?.longitud}"
    }

    fun redirectToGoogleMaps() {

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
        if (notesSharedViewModel.selectedNote == null) {
            if (!(titulo.value).isNullOrBlank() && !(body.value).isNullOrBlank()){
                println("===================================================")
                println(titulo.value)
                println(latitud.value)
                println(longitud.value)
                println(userId)
                println(Date.from(Instant.now()).toString())
                println(body.value)
                println("===================================================")
                insert(Note("", titulo.value!!, latitud.value!!, longitud.value!!, userId, Date.from(Instant.now()).toString(), body.value!!))
                titulo.value = ""
                body.value = ""
            }
        } else {
            if (!(titulo.value).isNullOrBlank() && !(body.value).isNullOrBlank()) {
                notesSharedViewModel.selectedNote?.id = notesSharedViewModel.selectedNote!!.id
                notesSharedViewModel.selectedNote?.titulo = titulo.value!!
                notesSharedViewModel.selectedNote?.latitud = latitud.value!!
                notesSharedViewModel.selectedNote?.longitud = longitud.value!!
                notesSharedViewModel.selectedNote?.user_id = userId
                notesSharedViewModel.selectedNote?.fecha = (Date.from(Instant.now()).toString())
                notesSharedViewModel.selectedNote?.body = body.value!!
                update(notesSharedViewModel.selectedNote!!)
                notesSharedViewModel.selectedNote = null
                titulo.value = ""
                body.value = ""
            }
        }
    }

    fun delete() {
        if (!(titulo.value).isNullOrBlank() && !(body.value).isNullOrBlank()) {
            notesSharedViewModel.selectedNote?.id = notesSharedViewModel.selectedNote!!.id
            deleteNote(notesSharedViewModel.selectedNote!!)
            notesSharedViewModel.selectedNote = null
            titulo.value = ""
            body.value = ""
        }
    }

    private fun checkIfValid() = !(titulo.value).isNullOrBlank()
            && !(body.value).isNullOrBlank()
}