package com.example.finalproject.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.models.Contact
import com.example.finalproject.models.Note
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class ContactsDetailViewModel(val contactsSharedViewModel: ContactsSharedViewModel,
                              val userId: String?, ): ViewModel() {
    val repository = contactsSharedViewModel.repository
    var isValid = MediatorLiveData<Boolean>()
    var contactNameText = MutableLiveData<String>()
    var contactEmail = MutableLiveData<String>()

    var titulo = MutableLiveData<String>()
    var body = MutableLiveData<String>()

    var latitud = MutableLiveData<Double>()
    var longitud = MutableLiveData<Double>()


    init {
        isValid.addSource(contactNameText) {
            isValid.value = checkIfValid()
        }
        isValid.addSource(contactEmail) {
            isValid.value = checkIfValid()
        }
    }

    fun updateTexts() {
        titulo.value = contactsSharedViewModel.selectedContact?.titulo
        body.value = contactsSharedViewModel.selectedContact?.body
    }

    fun insert(note: Note) = viewModelScope.launch{
        repository.insertToApi(note) // ahora usando el del api
    }

    fun update(note: Note) = viewModelScope.launch {
        println("por insertaaaaaaaaaaaaaaaaaaaaaaar")
        repository.update(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }

    fun save() {
        if (contactsSharedViewModel.selectedContact == null) {
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
                contactNameText.value=""
                contactEmail.value = ""
                titulo.value = ""
                body.value = ""
                println("se inserto DIOOOOOOOOOOOOOOOOOS")
            }
        } else {
            if (!(titulo.value).isNullOrBlank() && !(body.value).isNullOrBlank()) {
                contactsSharedViewModel.selectedContact?.id = contactsSharedViewModel.selectedContact!!.id
                contactsSharedViewModel.selectedContact?.titulo = titulo.value!!
                contactsSharedViewModel.selectedContact?.latitud = latitud.value!!
                contactsSharedViewModel.selectedContact?.longitud = longitud.value!!
                contactsSharedViewModel.selectedContact?.user_id = userId
                contactsSharedViewModel.selectedContact?.fecha = (Date.from(Instant.now()).toString())
                contactsSharedViewModel.selectedContact?.body = body.value!!
                update(contactsSharedViewModel.selectedContact!!)
                contactsSharedViewModel.selectedContact = null
                titulo.value = ""
                body.value = ""
            }
        }
    }

    fun delete() {
        if (!(titulo.value).isNullOrBlank() && !(body.value).isNullOrBlank()) {
            contactsSharedViewModel.selectedContact?.id = contactsSharedViewModel.selectedContact!!.id
            deleteNote(contactsSharedViewModel.selectedContact!!)
            contactsSharedViewModel.selectedContact = null
            titulo.value = ""
            body.value = ""
        }
    }

    fun selectContact(note: Note) {
        contactsSharedViewModel.selectedContact = note
        titulo.value = note.titulo
        body.value = note.body
    }

    private fun checkIfValid() = !(contactNameText.value).isNullOrBlank()
            && !(contactEmail.value).isNullOrBlank()
}