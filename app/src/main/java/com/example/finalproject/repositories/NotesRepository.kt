package com.example.finalproject.repositories

import com.example.finalproject.api.ApiService
import com.example.finalproject.models.Note
import com.example.finalproject.room.NoteDao
import kotlinx.coroutines.flow.flow

class NotesRepository(private val noteDao: NoteDao,
                      private val apiService: ApiService
) {

    val notes = noteDao.getAllNotes()

    suspend fun update(note: Note) {
        try {
            val response = apiService.updateNote(note)
            if (response.isSuccessful) {
                getAll().collect {
                    // Maneja la respuesta de getAll aquí si es necesario
                }
            } else {
                // Maneja el error de la respuesta no exitosa
                println("LASTIMOSAMENTE SALIO ERROR: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            // Maneja cualquier excepción que ocurra durante la solicitud de red
            println("EXCEPCIÓN DURANTE LA SOLICITUD: ${e.message}")
        }
    }

    suspend fun delete(note: Note) {

        try {
            val response = apiService.deleteNote(note)
            if (response.isSuccessful) {
                getAll().collect {
                    // Maneja la respuesta de getAll aquí si es necesario
                }
            } else {
                // Maneja el error de la respuesta no exitosa
                println("LASTIMOSAMENTE SALIO ERROR: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            // Maneja cualquier excepción que ocurra durante la solicitud de red
            println("EXCEPCIÓN DURANTE LA SOLICITUD: ${e.message}")
        }
    }

    fun getAll() = flow {
        // if hay internet has esto
        val result = apiService.getNotes("usuario2")
        if (result.isSuccessful && result.body() != null) {
            noteDao.deleteAll()
            noteDao.insertAll(result.body()!!)
            emit(true)
        } else {
            emit(false)
        }
        // else emit(false o errorCode de no internet)
    }

    suspend fun insertToApi(note: Note) {
        try {
            val response = apiService.postNotes(note)
            if (response.isSuccessful) {
                getAll().collect {
                    // Maneja la respuesta de getAll aquí si es necesario
                }
            } else {
                // Maneja el error de la respuesta no exitosa
                println("LASTIMOSAMENTE SALIO ERROR: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            // Maneja cualquier excepción que ocurra durante la solicitud de red
            println("EXCEPCIÓN DURANTE LA SOLICITUD: ${e.message}")
        }
    }
}
