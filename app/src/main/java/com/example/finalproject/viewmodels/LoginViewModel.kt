package com.example.finalproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.models.User
import com.example.finalproject.repositories.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var username: String = ""
    var password: String = ""

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun onLoginClicked() {
        viewModelScope.launch {
            try {
                val response = repository.login(User(username, password))
                if (response.isSuccessful && response.body()?.userId != null) {
                    repository.saveUserId(response.body()?.userId!!)
                    _loginResult.value = true
                } else {
                    _loginResult.value = false
                }
            } catch (e: Exception) {
                _loginResult.value = false
                // Handle exception
            }
        }
    }

    fun getUserId(): String? {
        var userId: String? = null
        runBlocking {
            userId = repository.getUserId()
        }
        return userId
    }
}