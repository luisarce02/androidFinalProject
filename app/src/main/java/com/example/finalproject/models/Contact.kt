package com.example.finalproject.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("contacts")
class Contact (
    @PrimaryKey
    @SerializedName("id")
    var id: String,
    @ColumnInfo
    @SerializedName("name")
    var name: String,
    @ColumnInfo
    @SerializedName("email")
    var email: String)
