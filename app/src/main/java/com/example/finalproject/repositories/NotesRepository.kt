package com.example.finalproject.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.finalproject.SharedPreferencesUserDataStore
import com.example.finalproject.api.ApiService
import com.example.finalproject.models.Note
import com.example.finalproject.room.NoteDao
import kotlinx.coroutines.flow.flow

class NotesRepository(private val noteDao: NoteDao,
                      private val apiService: ApiService,
                      val userDataStore: SharedPreferencesUserDataStore,
                        val context: Context
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
        if (isInternetAvailable(context)) {
            val result = apiService.getNotes(userDataStore.getUserId())
            if (result.isSuccessful && result.body() != null) {
                noteDao.deleteAll()
                noteDao.insertAll(result.body()!!)
                emit(true)
            } else {
                emit(false)
            }
        } else {
            emit(false)
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
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
