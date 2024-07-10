package com.example.finalproject.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.api.ApiService
import com.example.finalproject.api.RetrofitInstance
import com.example.finalproject.repositories.NotesRepository
import com.example.finalproject.room.NotesAppDatabase
import com.example.finalproject.viewmodels.NotesSharedViewModel

class NotesViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesSharedViewModel::class.java)) {
            val database = NotesAppDatabase.getInstance(context)
            val noteDao = database.noteDao
            val apiService = RetrofitInstance.getInstance()
                .create(ApiService::class.java)
            val repository = NotesRepository(noteDao, apiService)
            return NotesSharedViewModel(repository) as T
        }
        return super.create(modelClass)
    }
}
