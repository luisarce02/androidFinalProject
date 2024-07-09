package com.example.finalproject.api

import com.example.finalproject.models.LoginResponse
import com.example.finalproject.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    suspend fun login(@Body user: User): Response<LoginResponse>
}
