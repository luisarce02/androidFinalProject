package com.example.finalproject.api

import com.example.finalproject.models.LoginResponse
import com.example.finalproject.models.Note
import com.example.finalproject.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    @POST("/login")
    suspend fun login(@Body user: User): Response<LoginResponse>
    @GET("/notes")
    suspend fun getNotes(@Query("user_id") userId: String?): Response<ArrayList<Note>>
    @POST("/notes")
    suspend fun postNotes(@Body note: Note): Response<String>
    @PUT("/notes")
    suspend fun updateNote(@Body note: Note): Response<String>
    @HTTP(method = "DELETE", path = "notes", hasBody = true)
    suspend fun deleteNote(@Body note: Note): Response<String>
}
