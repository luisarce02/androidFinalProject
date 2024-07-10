package com.example.finalproject.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.models.Contact
import com.example.finalproject.models.Note
import com.example.finalproject.repositories.ContactsRepository
import kotlinx.coroutines.launch

class ContactsSharedViewModel(val repository: ContactsRepository): ViewModel() {

    var selectedContact: Note? = null

    val contacts = repository.notes
    val notes = repository.notes

    fun selectContact(note: Note) {
        selectedContact = note
    }

    fun getAllContacts() = viewModelScope.launch {
        // aqui podriamos usar nuestros propios error codes
        repository.getAll().collect() {result ->
            if (!result) {
                // mostrar mensaje de error.
            }
        }
    }
}
