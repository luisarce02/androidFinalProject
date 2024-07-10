package com.example.finalproject.repositories

import com.example.finalproject.api.ApiService
import com.example.finalproject.api.RetrofitInstance
import com.example.finalproject.models.LoginResponse
import com.example.finalproject.models.User
import com.example.finalproject.models.UserDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserRepository(private val userDataStore: UserDataStore) {

    private val api = RetrofitInstance.getInstance().create(ApiService::class.java)

    suspend fun login(user: User): Response<LoginResponse> {
        return withContext(Dispatchers.IO) {
            api.login(user)
        }
    }

    suspend fun saveUserId(userId: String) {
        withContext(Dispatchers.IO) {
            userDataStore.saveUserId(userId)
        }
    }

    suspend fun getUserId(): String? {
        return withContext(Dispatchers.IO) {
            userDataStore.getUserId()
        }
    }

    suspend fun deleteUserId() {
        return withContext(Dispatchers.IO) {
            userDataStore.deleteUserId()
        }
    }
}