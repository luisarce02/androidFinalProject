package com.example.finalproject.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalproject.models.Note

@Database(entities = [Note::class], version = 5)
abstract class NotesAppDatabase: RoomDatabase() {
    abstract val noteDao: NoteDao

    //Singleton
    companion object {
        @Volatile
        private var INSTANCE: NotesAppDatabase? = null
        fun getInstance(context: Context): NotesAppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, NotesAppDatabase::class.java, "contacts_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}
