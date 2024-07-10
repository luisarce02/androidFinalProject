package com.example.finalproject.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.api.ApiService
import com.example.finalproject.api.RetrofitInstance
import com.example.finalproject.repositories.ContactsRepository
import com.example.finalproject.room.ContactsAppDatabase
import com.example.finalproject.viewmodels.ContactsSharedViewModel

class ContactsViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsSharedViewModel::class.java)) {
            val database = ContactsAppDatabase.getInstance(context)
            val contactsDao = database.contactDao
            val contactsApiService = RetrofitInstance.getInstance()
                .create(ApiService::class.java)
            val repository = ContactsRepository(contactsDao, contactsApiService)
            return ContactsSharedViewModel(repository) as T
        }
        return super.create(modelClass)
    }
}
