package com.example.finalproject.models

import android.content.Context

interface UserDataStore {
    fun saveUserId(userId: String)
    fun getUserId(): String?
    fun deleteUserId()
}