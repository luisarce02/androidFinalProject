package com.example.finalproject.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.SharedPreferencesUserDataStore
import com.example.finalproject.viewmodels.NotesDetailViewModel
import com.example.finalproject.viewmodels.NotesSharedViewModel

class NoteDetailsViewModelFactory(val sharedViewModel: NotesSharedViewModel,
                                  val userDataStore: SharedPreferencesUserDataStore
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesDetailViewModel::class.java)) {
            return NotesDetailViewModel(sharedViewModel, userDataStore) as T
        }
        return super.create(modelClass)
    }
}