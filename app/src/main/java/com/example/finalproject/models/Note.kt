package com.example.finalproject.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("notes")
class Note (
    @PrimaryKey
    @SerializedName("id")
    var id: String,
    @ColumnInfo
    @SerializedName("titulo")
    var titulo: String,
    @ColumnInfo
    @SerializedName("latitud")
    var latitud: Double,
    @ColumnInfo
    @SerializedName("longitud")
    var longitud: Double,
    @ColumnInfo
    @SerializedName("user_id")
    var user_id: String?,
    @ColumnInfo
    @SerializedName("fecha")
    var fecha: String,
    @ColumnInfo
    @SerializedName("body")
    var body: String
)
