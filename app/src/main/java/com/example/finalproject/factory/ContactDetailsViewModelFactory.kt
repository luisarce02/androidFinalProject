package com.example.finalproject.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.viewmodels.ContactsDetailViewModel
import com.example.finalproject.viewmodels.ContactsSharedViewModel

class ContactDetailsViewModelFactory(val sharedViewModel: ContactsSharedViewModel,
                                     val userId: String?): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsDetailViewModel::class.java)) {
            return ContactsDetailViewModel(sharedViewModel, userId) as T
        }
        return super.create(modelClass)
    }
}