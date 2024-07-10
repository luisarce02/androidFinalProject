package com.example.finalproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalproject.models.Contact
import com.example.finalproject.models.Note

@Database(entities = [Note::class], version = 5)
abstract class ContactsAppDatabase: RoomDatabase() {
    abstract val contactDao: NoteDao

    //Singleton
    companion object {
        @Volatile
        private var INSTANCE: ContactsAppDatabase? = null
        fun getInstance(context: Context): ContactsAppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, ContactsAppDatabase::class.java, "contacts_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}
