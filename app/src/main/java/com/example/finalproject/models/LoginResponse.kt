package com.example.finalproject.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user_id")
    val userId: String?
)