package com.example.finalproject.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject.models.Note

@Dao
interface NoteDao {
    @Query("Select * from notes")
    fun  getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: List<Note>)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()
}