package com.example.finalproject.models


interface UserDataStore {
    fun saveUserId(userId: String)
    fun getUserId(): String?
    fun deleteUserId()
}