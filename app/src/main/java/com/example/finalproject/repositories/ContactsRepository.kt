package com.example.finalproject.repositories

import com.example.finalproject.api.ApiService
import com.example.finalproject.models.Contact
import com.example.finalproject.models.Note
import com.example.finalproject.room.ContactDao
import com.example.finalproject.room.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class ContactsRepository(private val contactDao: NoteDao,
                         private val contactsApiService: ApiService
) {

    //val contacts = contactDao.getAllContacts()
    val notes = contactDao.getAllNotes()

    /*
    suspend fun insert(contact: Contact) {
        contactDao.insertContact(contact)
    }

     */

    /*
    suspend fun update(note: Note) {
        //contactDao.updateContact(contact)
    }

     */

    suspend fun update(note: Note) {
        try {
            val response = contactsApiService.updateNote(note)
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
            val response = contactsApiService.deleteNote(note)
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

    /*
    fun getAll() = flow {
        // Ejecutar la llamada en un contexto IO
        val result = withContext(Dispatchers.IO) {
            contactsApiService.getNotes("usuario2").execute()
        }
        if (result.isSuccessful && result.body() != null) {
            // contactDao.insertAll(result.body()!!)
            println("Se insertoooooooooooooooooooooooooooo")
            emit(true)
        } else {
            emit(false)
        }
    }

     */

    /*
    fun getAll() = flow {
        // Ejecutar la llamada en un contexto IO
        val result = withContext(Dispatchers.IO) {
            contactsApiService.getNotes("usuario2")
        }
        if (result.isSuccessful && result.body() != null) {
            contactDao.insertAll(result.body()!!)
            emit(true)
        } else {
            emit(false)
        }
    }

     */

    fun getAll() = flow {
        // if hay internet has esto
        val result = contactsApiService.getNotes("usuario2")
        if (result.isSuccessful && result.body() != null) {
            contactDao.deleteAll()
            contactDao.insertAll(result.body()!!)
            emit(true)
        } else {
            emit(false)
        }
        // else emit(false o errorCode de no internet)
    }

    suspend fun insertToApi(note: Note) {
        try {
            val response = contactsApiService.postNotes(note)
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
